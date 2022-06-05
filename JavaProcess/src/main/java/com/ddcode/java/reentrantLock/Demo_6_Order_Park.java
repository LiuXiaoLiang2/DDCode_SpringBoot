package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 顺序执行
 */
@Slf4j(topic = "c.park")
public class Demo_6_Order_Park {

    public static final Object obj = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                LockSupport.park();
                log.info("t1线程, 执行, 输出 1");
            }
        };

        t1.start();

        new Thread("t2"){
            @Override
            public void run() {
                log.info("t2线程, 执行, 输出 2");
                LockSupport.unpark(t1);
            }
        }.start();
    }
}
