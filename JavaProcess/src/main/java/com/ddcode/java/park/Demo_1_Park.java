package com.ddcode.java.park;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.park")
public class Demo_1_Park {

    public static void main(String[] args) throws InterruptedException {
        //定义t1线程
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        },"t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
