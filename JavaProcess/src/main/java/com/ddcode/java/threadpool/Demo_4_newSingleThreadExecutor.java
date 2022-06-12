package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j(topic = "c.newSingleThreadExecutor")
public class Demo_4_newSingleThreadExecutor {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(()->{
           log.info("1");
           int i = 1 / 0;
        });
        executorService.execute(()->{
            log.info("2");
        });
        executorService.execute(()->{
            log.info("3");
        });
    }
}
