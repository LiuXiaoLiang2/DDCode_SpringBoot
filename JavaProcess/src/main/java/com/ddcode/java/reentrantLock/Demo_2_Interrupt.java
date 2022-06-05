package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.interrupt")
public class Demo_2_Interrupt {

    //创建锁对象
    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                //创建可打断锁
                try {
                    log.debug("t1线程,开始尝试获取锁");
                    //reentrantLock.lockInterruptibly();
                    reentrantLock.lock();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.debug("t1线程,在等待锁的过程中被打断");
                    //这个时候我们要return,不再执行后面的代码
                    return;
                }

                try {
                    log.info("获得锁, 开始执行代码");
                } finally {
                    //最后一定要释放锁
                    reentrantLock.unlock();
                }
            }
        };

        //主线程先获取到锁
        log.info("主线程首先获得锁");
        reentrantLock.lock();
        //然后再开启t1线程, t1线程就是开启了,也是在阻塞
        t1.start();
        //主线程执行业务2s
        TimeUnit.SECONDS.sleep(2);
        //打断正在阻塞的t1线程, 抛出InterruptedException异常
        log.info("打断t1线程");
        t1.interrupt();
    }
}
