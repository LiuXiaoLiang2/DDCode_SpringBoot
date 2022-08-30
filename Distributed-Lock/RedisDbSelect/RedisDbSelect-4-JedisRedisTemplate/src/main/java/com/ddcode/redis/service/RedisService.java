package com.ddcode.redis.service;

import com.ddcode.redis.annotation.RedisSelect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisService {

    @Resource
    private StringRedisTemplate redisTemplate;

    @RedisSelect(3)
    public void addName(){
        redisTemplate.opsForValue().set("name", "addName3");
    }
}
