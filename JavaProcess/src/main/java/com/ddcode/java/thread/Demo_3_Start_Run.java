package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 比较Thread的start和run方法区别
 */
@Slf4j(topic = "c.stat_run")
public class Demo_3_Start_Run {

    public static void main(String[] args) {
        start();
    }

    public static void run(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                log.info("调用run方法");
            }
        };
        thread.run();
    }

    public static void start(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                log.info("调用start方法");
            }
        };
        thread.start();
    }
}
