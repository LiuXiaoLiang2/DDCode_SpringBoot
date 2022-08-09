package com.guigu.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 */
@Slf4j(topic = "c.Demo_1_Thread")
public class Demo_1_Thread {

    public static void main(String[] args) {

        log.info("主线程开始执行");
        Thread thread = new Thread(() -> {
            log.info("ThreadName {} isDaemon {}" , Thread.currentThread().getName() , Thread.currentThread().isDaemon());
            while (true){
                log.info("子线程一直执行");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //设置子线程为守护线程
        thread.setDaemon(true);
        thread.start();
        log.info("主线程执行完毕");
    }
}
