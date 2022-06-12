package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 使用 submit方法
 */
@Slf4j(topic = "c.shutdown")
public class Demo_8_ThreadPool_Shutdown {
    public static void main(String[] args) throws Exception {
        shutdown();
    }



    public static void shutdown() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> future1 = executorService.submit(() -> {
            log.info("执行任务1");
            TimeUnit.SECONDS.sleep(3);
            log.info("执行任务1, 结束");
            return "done1";
        });

        Future<String> future2 = executorService.submit(() -> {
            log.info("执行任务2");
            TimeUnit.SECONDS.sleep(2);
            log.info("执行任务2, 结束");
            return "done2";
        });

        log.info("主线程执行 shutdown 开始");
        executorService.shutdownNow();
        log.info("主线程执行 shutdown 结束");

        Future<String> future3 = executorService.submit(() -> {
            log.info("执行任务3");
            TimeUnit.SECONDS.sleep(1);
            return "done3";
        });


    }
}
