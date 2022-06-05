package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.repeat")
public class Demo_1_Repeat {

    //创建锁对象
    static  ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                reentrantLock.lock();
                method1();
            }
        };
        t1.start();
    }

    public static void method1(){
        log.info("执行method1");
        reentrantLock.lock();
        method2();
    }

    public static void method2(){
        log.info("执行method2");
    }

}

