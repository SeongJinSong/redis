package spring.redis.stock;

import lombok.Getter;

@Getter
public class Stock {
    String name;
    String keyId;
    int amount;

    public Stock(String name, String keyId, int amount) {
        this.name = name;
        this.keyId = keyId;
        this.amount = amount;
    }
}
