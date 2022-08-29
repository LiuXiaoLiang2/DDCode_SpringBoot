package com.ddcode.redis.controller;

import com.ddcode.redis.util.RedisUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {

    @Resource
    private RedisUtils redisUtils;

    @RequestMapping("/putRedisData")
    public String putRedisData(){
        redisUtils.setString("db0", "db0value", 0);
        redisUtils.setString("db1", "db1value", 1);
        redisUtils.setString("db2", "db2value", 2);
        return "ok";
    }
}
