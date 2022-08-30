package com.ddcode.redis.controller;

import com.ddcode.redis.annotation.RedisSelect;
import com.ddcode.redis.service.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {

    @Resource
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/putOne")
    @RedisSelect(0)
    public String putOne(){
        redisTemplate.opsForValue().set("name", "one");
        return "ok";
    }

    @RequestMapping("/getOne")
    @RedisSelect(0)
    public String getOne(){
        String name = redisTemplate.opsForValue().get("name");
        return name;
    }

    @RequestMapping("/putTwo")
    @RedisSelect(1)
    public String putTwo(){
        redisTemplate.opsForValue().set("name", "two");
        return "ok";
    }

    @RequestMapping("/getTwo")
    @RedisSelect(1)
    public String getTwo(){
        String name = redisTemplate.opsForValue().get("name");
        return name;
    }

    @Resource
    private RedisService redisService;

    @RequestMapping("/useService")
    public String useService(){
        redisService.addName();
        return "ok";
    }
}
