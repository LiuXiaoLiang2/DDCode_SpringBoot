package com.ddcode.redisson.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisson")
@Slf4j
public class RedissonController {

    @Resource
    private Redisson redisson;

    @RequestMapping("/getLock")
    public String getLock() throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        log.info("currentThreadName {} 开始执行", currentThreadName);
        String lockey = "sku:1:info";
        RLock redissonLock  = null;
        try {
            //获取redis锁
            log.info("currentThreadName {} 开始执行, 尝试加锁", currentThreadName);
            // 已经上锁
            redissonLock = redisson.getLock(lockey);
            //设置锁的有效时间:5s
            redissonLock.lock(5, TimeUnit.SECONDS);
            log.info("currentThreadName {} 开始执行, 尝试加锁成功, 开始执行业务代码", currentThreadName);
            TimeUnit.SECONDS.sleep(8);
            log.info("currentThreadName {} 开始执行, 尝试加锁成功, 执行业务代码结束", currentThreadName);
        } catch (Exception e) {

        } finally {
            if(redissonLock != null && redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()){
                log.info("currentThreadName {} 开始执行, 开始释放锁", currentThreadName);
                redissonLock.unlock();
            }
        }
        return "ok";
    }
}
