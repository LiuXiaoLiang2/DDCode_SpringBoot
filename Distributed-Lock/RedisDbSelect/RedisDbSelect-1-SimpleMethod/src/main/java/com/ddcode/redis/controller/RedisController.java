package com.ddcode.redis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class RedisController {

    @Resource(name = "redisOneDb")
    private RedisTemplate redisTemplateOne;

    @Resource(name = "redisDbTwo")
    private RedisTemplate redisTemplateTwo;


    @RequestMapping("/putRedisDbOne")
    public String putRedisDbOne(){
        redisTemplateOne.opsForValue().set("putRedisDbOne", "lucy");
        return "ok";
    }

    @RequestMapping("/putRedisDbTwo")
    public String putRedisDbTwo(){
        redisTemplateTwo.opsForValue().set("putRedisDbTwo", "jack");
        return "ok";
    }


    @RequestMapping("/getRedisDbOne")
    public String getRedisDbOne(){
        return redisTemplateOne.opsForValue().get("putRedisDbOne").toString();
    }

    @RequestMapping("/getRedisDbTwo")
    public String getRedisDbTwo(){
        return redisTemplateTwo.opsForValue().get("putRedisDbTwo").toString();
    }
}
