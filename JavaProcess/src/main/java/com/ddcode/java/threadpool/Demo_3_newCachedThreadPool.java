package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.newCachedThreadPool")
public class Demo_3_newCachedThreadPool {

    public static void main(String[] args) throws InterruptedException {
        //test1();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(()->{
            try {
                log.info("任务1, 开始执行");
                TimeUnit.SECONDS.sleep(1);
                log.info("任务1, 结束");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()->{
            try {
                log.info("任务2, 开始执行");
                TimeUnit.SECONDS.sleep(1);
                log.info("任务2, 结束");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public static void test1() throws InterruptedException {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();

        //开启一个线程, 向队列中存储数据, 存储两个数据
        new Thread(() -> {
            try {
                log.debug("putting {} ", 1);
                integers.put(1);
                log.debug("{} putted...", 1);
                log.debug("putting...{} ", 2);
                integers.put(2);
                log.debug("{} putted...", 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        // 休眠1s获取一个数据
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();

        // 再休眠1s, 再获取一个数据
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();
    }
}
