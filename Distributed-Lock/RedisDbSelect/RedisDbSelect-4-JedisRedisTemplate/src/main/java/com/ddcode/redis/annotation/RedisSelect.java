package com.ddcode.redis.annotation;

import java.lang.annotation.*;

/**
 * 定义注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisSelect {
    /**
     * redis库
     * @return
     */
    int value() default 0;
}
