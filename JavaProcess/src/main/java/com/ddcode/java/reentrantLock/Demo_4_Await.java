package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 await 方法
 */
@Slf4j(topic = "c.await")
public class Demo_4_Await {

    //创建锁对象
    static ReentrantLock lock = new ReentrantLock();
    //创建烟的休息室
    static Condition waitCigaretteQueue = lock.newCondition();
    //创建外卖的休息室
    static Condition waitbreakfastQueue = lock.newCondition();
    //判断是否烟到了
    static volatile boolean hasCigrette = false;
    //判断外卖是否到了
    static volatile boolean hasBreakfast = false;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread("小南") {
            @Override
            public void run() {
                log.info("小南, 等待阻塞烟");
                try {
                    lock.lock();
                    while (!hasCigrette){
                        try {
                            waitCigaretteQueue.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("小南, 烟到了, 可以干活了");
                } finally {
                    log.info("小南, 释放锁");
                    lock.unlock();
                }
            }
        };

        Thread t2 = new Thread("小女") {
            @Override
            public void run() {
                log.info("小女, 等待阻塞外卖");
                try {
                    lock.lock();
                    while (!hasBreakfast){
                        try {
                            waitbreakfastQueue.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("小女, 外卖到了, 可以干活了");
                } finally {
                    log.info("小女, 释放锁");
                    lock.unlock();
                }
            }
        };

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        new Thread("送烟的"){
            @Override
            public void run() {
                lock.lock();
                try {
                    log.info("开始送烟");
                    hasCigrette = true;
                    //专门唤醒等烟的
                    waitCigaretteQueue.signal();
                } finally {
                    lock.unlock();
                }
            }
        }.start();

        TimeUnit.SECONDS.sleep(2);

        new Thread("送外卖的"){
            @Override
            public void run() {
                lock.lock();
                try {
                    log.info("开始送外卖");
                    hasBreakfast = true;
                    //专门唤醒等烟的
                    waitbreakfastQueue.signal();
                } finally {
                    lock.unlock();
                }
            }
        }.start();

    }
}
