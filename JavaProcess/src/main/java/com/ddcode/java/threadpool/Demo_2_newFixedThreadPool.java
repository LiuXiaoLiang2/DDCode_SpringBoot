package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "c.Demo_2_newFixedThreadPool")
public class Demo_2_newFixedThreadPool {

    public static void main(String[] args) {

//        //创建线程池,
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//
//        // 提交三个任务执行
//        executorService.submit(()->{
//            log.info("1");
//        });
//        executorService.submit(()->{
//            log.info("2");
//        });
//        executorService.submit(()->{
//            log.info("3");
//        });


        //创建线程池,
        ExecutorService executorService2 = Executors.newFixedThreadPool(2, new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MyPool-" + atomicInteger.incrementAndGet());
            }
        });


        // 提交三个任务执行
        executorService2.submit(()->{
            log.info("1");
        });
        executorService2.submit(()->{
            log.info("2");
        });
        executorService2.submit(()->{
            log.info("3");
        });
    }
}
