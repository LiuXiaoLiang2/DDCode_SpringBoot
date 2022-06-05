package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.timeout")
public class Demo_3_Timeout {

    //创建锁对象
    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    log.info("t1线程, 开始阻塞等待锁");
                    boolean tryLock = reentrantLock.tryLock(1, TimeUnit.SECONDS);
                    if(!tryLock){
                        log.info("t1线程, 等待超时的时间到, 被主动唤醒");
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    log.info("t1线程, 获得锁");
                } finally {
                    //记得释放锁
                    reentrantLock.unlock();
                }
            }
        };

        //主线程先获得锁
        log.info("主线程得到锁");
        reentrantLock.lock();
        //同时开启子线程
        t1.start();
        //主线程休眠
        TimeUnit.SECONDS.sleep(3);
        //主线程释放锁
        log.info("主线程释放锁");
        reentrantLock.unlock();
    }
}
