package spring.redis.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockServiceSimulate stockServiceSimulate;
    @PostMapping("item/{itemId}")
    void addItem(@PathVariable Long itemId) {
        stockServiceSimulate.addItem(itemId);
    }
    @PutMapping("item/{itemId}/nolock")
    void buyItemNoLock(@PathVariable Long itemId) throws InterruptedException {
        stockServiceSimulate.simulateNoLock(itemId);
    }

    @PutMapping("item/{itemId}/lock")
    void buyItem(@PathVariable Long itemId) throws InterruptedException {
        stockServiceSimulate.simulateLock(itemId);
    }

    @PutMapping("item/{itemId}/lock-sleep")
    void buyItemSleep(@PathVariable Long itemId) throws InterruptedException {
        stockServiceSimulate.simulateLockSleep(itemId);
    }
}
