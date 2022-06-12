package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * 使用 submit方法
 */
@Slf4j(topic = "c.submit")
public class Demo_6_ThreadPool_Submit {
    public static void main(String[] args) throws Exception {
//        useFuture();
//        useSubmit();
//        useSubmitPlus();
        useSubmitPlus3();
    }


    /**
     * 复习future的使用
     */
    public static void useFuture() throws Exception {
        Callable<String> callable = new Callable<String>(){
            @Override
            public String call() throws Exception {

                log.info("执行线程任务1");
                TimeUnit.SECONDS.sleep(1);
                return "done1";
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread t1 = new Thread(future, "t1");

        Callable<String> callable2 = new Callable<String>(){
            @Override
            public String call() throws Exception {

                log.info("执行线程任务2");
                TimeUnit.SECONDS.sleep(2);
                return "done2";
            }
        };
        FutureTask<String> future2 = new FutureTask<String>(callable2);
        Thread t2 = new Thread(future2, "t1");

        t1.start();
        t2.start();

        log.info("主线程阻塞等待");
        String result1 = future.get();
        log.info("线程1返回结果 {}", result1);
        String result2 = future2.get();
        log.info("线程2返回结果 {}", result2);
    }

    public static void useSubmit() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> future1 = executorService.submit(() -> {
            log.info("执行任务1");
            TimeUnit.SECONDS.sleep(3);
            return "done1";
        });

        Future<String> future2 = executorService.submit(() -> {
            log.info("执行任务2");
            TimeUnit.SECONDS.sleep(2);
            return "done2";
        });

        Future<String> future3 = executorService.submit(() -> {
            log.info("执行任务2");
            TimeUnit.SECONDS.sleep(1);
            return "done3";
        });

        log.info("主线程阻塞等待");
        String result1 = future1.get();
        log.info("任务1线程返回 {}", result1);
        String result2 = future2.get();
        log.info("任务2线程返回 {}", result2);
        String result3 = future3.get();
        log.info("任务3线程返回 {}", result3);
    }

    public static void useSubmitPlus() throws ExecutionException, InterruptedException {
        final String[] mutable = new String[1];
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String[]> submit = executorService.submit(() -> {
            log.info("执行任务1");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mutable[0] = "howdy";
        }, mutable);
        String[] result = submit.get();
        log.info("result {}", result);
    }


    public static void useSubmitPlus2() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit = executorService.submit(() -> {
            log.info("执行子线程任务");
        });
        submit.get();
    }

    public static void useSubmitPlus3() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> submit = executorService.submit(() -> {
            log.info("执行子线程任务");
            return "done";
        });
        String result = submit.get();
        log.info("result {}", result);
    }
}
