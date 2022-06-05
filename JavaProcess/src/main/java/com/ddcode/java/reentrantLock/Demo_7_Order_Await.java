package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序执行
 */
@Slf4j(topic = "c.park")
public class Demo_7_Order_Await {

   private static ReentrantLock reentrantLock = new ReentrantLock();


    public static void main(String[] args) {

        Condition condition = reentrantLock.newCondition();

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    condition.await();
                    log.info("t1线程, 执行, 输出 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };

        t1.start();

        new Thread("t2"){
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    log.info("t2线程, 执行, 输出 2");
                    condition.signal();
                } finally {
                    reentrantLock.unlock();
                }

            }
        }.start();
    }
}
