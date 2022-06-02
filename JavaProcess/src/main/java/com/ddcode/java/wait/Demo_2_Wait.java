package com.ddcode.java.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.wait")
public class Demo_2_Wait {

    private static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        new Thread("t1"){
            @Override
            public void run() {
                //同步代码块
                synchronized (obj){
                    log.info("t1 线程, 开始执行同步代码块, 执行 wait");
                    try {
                        obj.wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("t1 线程, 被唤醒, 继续执行");
                }

            }
        }.start();


        new Thread("t2"){
            @Override
            public void run() {
                //同步代码块
                synchronized (obj){
                    log.info("t2 线程, 开始执行同步代码块, 执行 wait");
                    try {
                        obj.wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("t2 线程, 被唤醒, 继续执行");
                }

            }
        }.start();


        TimeUnit.SECONDS.sleep(2);

        synchronized (obj){
            log.info("主线程 开始唤醒所有线程");
            obj.notify();
        }

    }
}
