package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 测试守护线程
 */
@Slf4j(topic = "c.daemon")
public class Demo_10_Daemon {

    public static void main(String[] args) {
        daemon();
    }

    public static void daemon(){
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    log.info("t1线程开始执行");
                    TimeUnit.SECONDS.sleep(2);
                    log.info("t1线程结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //设置t1是守护线程
        t1.setDaemon(true);
        t1.start();
        try {
            log.info("主线程开始执行");
            TimeUnit.SECONDS.sleep(1);
            log.info("主线程结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
