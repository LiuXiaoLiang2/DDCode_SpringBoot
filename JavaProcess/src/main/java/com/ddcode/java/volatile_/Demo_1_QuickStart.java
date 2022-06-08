package com.ddcode.java.volatile_;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.volatile")
public class Demo_1_QuickStart {

    static volatile boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
                // ....
            }
        });

        t.start();
        TimeUnit.SECONDS.sleep(1);
        run = false; // 线程t不会如预想的停下来
    }
}
