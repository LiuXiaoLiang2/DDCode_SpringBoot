package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 使用 invokeAll方法
 */
@Slf4j(topic = "c.invokeAny")
public class Demo_7_ThreadPool_invokeAny {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        String result = executorService.invokeAny(Arrays.asList(
                () -> {
                    log.info("执行任务1");
                    TimeUnit.SECONDS.sleep(1);
                    return "done1";
                },
                () -> {
                    log.info("执行任务2");
                    TimeUnit.SECONDS.sleep(3);
                    return "done2";
                }
                ,
                () -> {
                    log.info("执行任务3");
                    TimeUnit.SECONDS.sleep(1);
                    return "done3";
                }
                ,
                () -> {
                    log.info("执行任务4");
                    TimeUnit.SECONDS.sleep(2);
                    return "done4";
                }
                //加超时时间最多等1s
        ), 1, TimeUnit.SECONDS);
        log.info("result {}", result);
    }
}
