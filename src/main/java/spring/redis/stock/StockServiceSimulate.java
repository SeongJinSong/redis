package spring.redis.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceSimulate {
    private final StockService stockService;

    public void simulateLock(Long itemId) throws InterruptedException {
        final int people = 100;
        final int count = 2;
        String stockKey = stockService.keyResolver("item"+itemId, String.valueOf(itemId));
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyWorker(stockKey, count, countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();

        final int currentCount = stockService.currentStock(stockService.keyResolver("item"+itemId, String.valueOf(itemId)));
        log.info("currentCount = {}", currentCount);
    }

    public void simulateNoLock(Long itemId) throws InterruptedException {

        final int people = 100;
        final int count = 2;
        String stockKey = stockService.keyResolver("item"+itemId, String.valueOf(itemId));
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyNoLockWorker(stockKey, count, countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();

        final int currentCount = stockService.currentStock(stockKey);
        log.info("currentCount = {}", currentCount);

    }

    public void addItem(Long itemId) {
        final String name = "item";
        final int amount = 100;
        String stockKey = stockService.keyResolver("item"+itemId, String.valueOf(itemId));
        stockService.setStock(stockKey, amount);
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
