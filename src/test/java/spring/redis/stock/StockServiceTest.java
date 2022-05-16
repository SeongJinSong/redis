package spring.redis.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class StockServiceTest {
    @Autowired
    StockService stockService;
    String stockKey;
    Stock item;

    @BeforeEach
    void stock_key_setting(){
        final String name = "item1";
        final String keyId = "1";
        final int amount = 100;
        final Stock item = new Stock(name, keyId, amount);

        this.stockKey = stockService.keyResolver(item.getName(), item.getKeyId());
        this.item = item;
        stockService.setStock(this.stockKey, amount);
    }

    @Test
    @Order(1)
    void stock_quantity_confirm(){
        final int amount = this.item.getAmount();
        final int currentCount = stockService.currentStock(stockKey);

        assertEquals(amount, currentCount);
    }

    @Test
    @Order(2)
    void stock_count_decrease(){
        final int amount = this.item.getAmount();
        final int count = 2;

        stockService.decrease(this.stockKey, count);

        final int currentCount = stockService.currentStock(stockKey);
        assertEquals(amount - count, currentCount);
    }

    @Test
    @Order(3)
    void there_are_100_items_and_100_people_order_2_each() throws InterruptedException {
        final int people = 100;
        final int count = 2;
        final int soldOUt = 0;
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyNoLockWorker(this.stockKey, count, countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();

        final int currentCount = stockService.currentStock(stockKey);
        //assertEquals(soldOUt, currentCount);
    }

    @Test
    @Order(4)
    void lock_there_are_100_items_and_100_people_order_2_each() throws InterruptedException {
        final int people = 100;
        final int count = 2;
        final int soldOut = 0;
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyWorker(this.stockKey, count, countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();

        final int currentCount = stockService.currentStock(this.stockKey);
        assertEquals(soldOut, currentCount);
    }

    private class BuyWorker implements Runnable{
        private String stockKey;
        private int count;
        private CountDownLatch countDownLatch;

        public BuyWorker(String stockKey, int count, CountDownLatch countDownLatch) {
            this.stockKey = stockKey;
            this.count = count;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run(){
            stockService.decrease(this.stockKey, count);
            countDownLatch.countDown();
        }
    }

    private class BuyNoLockWorker implements Runnable {
        private String stockKey;
        private int count;
        private CountDownLatch countDownLatch;

        public BuyNoLockWorker(String stockKey, int count, CountDownLatch countDownLatch) {
            this.stockKey = stockKey;
            this.count = count;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            stockService.decreaseNoLock(this.stockKey, count);
            countDownLatch.countDown();
        }
    }

}