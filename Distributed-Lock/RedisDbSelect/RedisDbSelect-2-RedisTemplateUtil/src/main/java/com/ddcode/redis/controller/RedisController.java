package com.ddcode.redis.controller;

import com.ddcode.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class RedisController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/putRedisData")
    public String putRedisData(Integer dbIndex){

        redisUtil.setDbIndex(dbIndex);
        boolean set = redisUtil.set("db-" + dbIndex, "jack" + dbIndex);
        log.info("dbIndex {} , result {}", dbIndex, set);
        return "ok";
    }
}
