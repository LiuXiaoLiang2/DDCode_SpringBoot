package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用execute方法
 */
@Slf4j(topic = "c.execute")
public class Demo_5_ThreadPool_execute {

    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(()->{
            log.info("执行任务1");
        });
        executorService.execute(()->{
            log.info("执行任务2");
        });
        executorService.execute(()->{
            log.info("执行任务3");
        });
    }
}
