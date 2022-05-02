package spring.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Slf4j
@Component
public class CacheKeyCreator {
    public static final String DELIMITER = ":";
    public static final String ROOM_MEMBER = "roomMember";

    public static Object createKey(String a0, String a1) {
        String result = new StringJoiner(DELIMITER).add(a0).add(a1).toString();
        log.info("createKey result:{}", result);
        return result;
    }
}
