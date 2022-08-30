package com.ddcode.redis.aspect;

import com.ddcode.redis.annotation.RedisSelect;
import com.ddcode.redis.config.RedisSelectSupport;
import com.ddcode.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class RedisAspect {

    @Resource
    private RedisUtils redisUtils;

    int defaultDataBase = 0;

    @Around("@annotation(com.ddcode.redis.annotation.RedisSelect)")
    public Object configRedis(ProceedingJoinPoint point) throws Throwable{
        log.info("进入aop");
        int db = defaultDataBase;
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();

            RedisSelect config = method.getAnnotation(RedisSelect.class);
            log.info("进入aop, value {}", config.value());
            if(config != null){
                db = config.value();
            }
            RedisSelectSupport.select(db);
            return point.proceed();
        } finally {
            //如果报错, 选择默认是0库
            RedisSelectSupport.select(defaultDataBase);
            log.debug("进入aop {} to {}", db, defaultDataBase);
        }
    }
}
