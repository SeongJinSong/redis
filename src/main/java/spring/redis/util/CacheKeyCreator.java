package spring.redis.util;

import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class CacheKeyCreator {
    public static final String DELIMITER = ":";
    public static final String ROOM_MEMBER = "roomMember";

    public static Object createKey(String a0, String a1) {
        return new StringJoiner(DELIMITER).add(a0).add(a1).toString();
    }
}
