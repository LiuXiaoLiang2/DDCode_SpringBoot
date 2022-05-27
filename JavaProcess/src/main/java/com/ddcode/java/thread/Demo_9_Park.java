package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用park方法打断线程
 */
@Slf4j(topic = "c.park")
public class Demo_9_Park {

    public static void main(String[] args) throws InterruptedException {
        park2();
    }

    public static void park() throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                log.info("t1线程: 开始执行");
                log.info("t1线程: 主动被挂起");
                LockSupport.park();
                log.info("t1线程: 线程状态 {}" , Thread.currentThread().isInterrupted());
                log.info("t1线程: 被挂起");
                log.info("t1线程: 继续执行");
            }
        };

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }

    public static void park2() throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                log.info("t1线程: 开始执行");
                log.info("t1线程: 主动被挂起");
                LockSupport.park();
                log.info("t1线程: 线程状态 {}" , Thread.currentThread().isInterrupted());
                log.info("t1线程: 被挂起");
                log.info("t1线程: 继续执行");
                //尝试再次被挂起
                log.info("t1线程: 尝试再次被挂起");
                LockSupport.park();
                log.info("t1线程: 尝试再次被挂起, 是否立马执行");
            }
        };

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }

}
