package com.ddcode.redis.config;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SelectTableRedisTemplate extends StringRedisTemplate {

    /**
     * connection连接的预处理
     * 每次请求使用redisTemplate,都会先执行改方法
     * @param connection
     * @param existingConnection
     * @return
     */
    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        Integer db;
        if((db = RedisSelectSupport.getSelect()) != null){
            connection.select(db);
        }
        return super.preProcessConnection(connection, existingConnection);
    }
}
