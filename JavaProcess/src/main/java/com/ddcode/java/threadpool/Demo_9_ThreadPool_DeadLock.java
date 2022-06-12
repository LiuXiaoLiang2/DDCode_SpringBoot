package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j(topic = "c.deadlock")
public class Demo_9_ThreadPool_DeadLock {

    static final List<String> MENU = Arrays.asList("地三鲜", "宫保鸡丁", "辣子鸡丁", "烤鸡翅");
    static Random RANDOM = new Random();
    static String cooking() {
        return MENU.get(RANDOM.nextInt(MENU.size()));
    }

    public static void main(String[] args) {

        ExecutorService waiterPool = Executors.newFixedThreadPool(1);
        ExecutorService cookPool = Executors.newFixedThreadPool(1);

        //如果有两个客人

        // 线程1
        waiterPool.execute(() -> {
            // 线程1执行点餐
            log.debug("waiterPool线程1处理点餐...");

            // 线程1 又给线程池提交做菜的任务, 此时线程2还没执行完
            Future<String> f = cookPool.submit(() -> {
                log.debug("cookPool线程1 做菜");
                return cooking();
            });


            try {
                // 线程1阻塞等待
                log.debug("waiterPool线程1 上菜: {}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });

        // 线程2
        waiterPool.execute(() -> {
            //线程2 执行点餐
            log.debug("waiterPool线程2处理点餐...");

            // 线程2 又给线程池提交做菜的任务, 此时线程1还没执行完
            Future<String> f = cookPool.submit(() -> {
                log.debug("cookPool线程2 做菜");
                return cooking();
            });


            try {
                // 线程2阻塞等待
                log.debug("waiterPool线程2 上菜: {}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });
    }

}
