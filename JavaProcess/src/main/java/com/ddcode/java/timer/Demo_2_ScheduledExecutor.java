package com.ddcode.java.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j(topic = "c.ScheduledExecutor")
public class Demo_2_ScheduledExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        noResult();
//        havingResult();
//        moreTaskHavingResult();
//        scheduleAtFixedRate();
        scheduleWithFixedDelay();
    }

    public static void noResult() throws ExecutionException, InterruptedException {
        //创建线程池
        //scheduledExecutor
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        log.info("schedule 执行");
        ScheduledFuture<?> schedule = scheduledExecutor.schedule(() -> {
            log.info("执行任务1");
        }, 1, TimeUnit.SECONDS);
        Object result = schedule.get();
        log.info("schedule 返回 {}", result);
    }

    public static void havingResult() throws ExecutionException, InterruptedException {
        //创建线程池
        //scheduledExecutor
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        log.info("schedule 执行");

        ScheduledFuture<String> schedule = scheduledExecutor.schedule(() -> {
            log.info("执行任务1");
            return "任务执行结束";
        }, 2, TimeUnit.SECONDS);
        log.info("schedule 使用get方法阻塞等待");
        String result = schedule.get();
        log.info("schedule 返回 {}", result);
    }


    public static void moreTaskHavingResult() throws ExecutionException, InterruptedException {
        //创建线程池
        //scheduledExecutor
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        log.info("schedule 执行");

        ScheduledFuture<String> schedule1 = scheduledExecutor.schedule(() -> {
            log.info("执行任务1");
            int i = 1 / 0;
            return "任务执行结束1";
        }, 2, TimeUnit.SECONDS);

        ScheduledFuture<String> schedule2 = scheduledExecutor.schedule(() -> {
            log.info("执行任务2");
            return "任务执行结束2";
        }, 2, TimeUnit.SECONDS);
        log.info("schedule 使用get方法阻塞等待");
        log.info("schedule1 返回 {}, schedule2 返回结果 {}", schedule1.get(), schedule2.get());
    }

    public static void scheduleAtFixedRate() throws ExecutionException, InterruptedException {
        //创建线程池
        //scheduledExecutor
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        log.info("schedule 执行");
        scheduledExecutor.scheduleAtFixedRate(() -> {
            log.info("执行任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static void scheduleWithFixedDelay() throws ExecutionException, InterruptedException {
        //创建线程池
        //scheduledExecutor
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        log.info("schedule 执行");
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            log.info("执行任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

}
