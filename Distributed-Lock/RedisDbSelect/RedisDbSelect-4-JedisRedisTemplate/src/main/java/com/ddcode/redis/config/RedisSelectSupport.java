package com.ddcode.redis.config;

/**
 * 当前线程中存储需要操作的redis的哪个库
 */
public class RedisSelectSupport {
    private static final ThreadLocal<Integer> SELECT_CONTEXT = new ThreadLocal<>();

    public static void select(int db){
        SELECT_CONTEXT.set(db);
    }

    public static Integer getSelect(){
        return SELECT_CONTEXT.get();
    }
}
