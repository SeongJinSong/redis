package spring.redis.util;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    // API
    int times() default 10;

    // redis
    int second() default 10;
}
