package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 使用线程的sleep方法
 */
@Slf4j(topic = "c.sleep")
public class Demo_4_Sleep {
    public static void main(String[] args) {
        sleepInterrupt();
    }

    public static void sleepState(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    log.info("子线程执行休眠 2s");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        log.info("子线程的状态 {}", thread.getState());
        //让主线程也休眠1s,这样过了1s后查看子线程状态肯定还是休眠
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这时候再次查看子线程状态
        log.info("再次查看子线程的状态 {}", thread.getState());
    }


    public static void sleepInterrupt(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    log.info("子线程执行休眠 10s");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.error("子线程被打断 ," , e);
                    e.printStackTrace();
                }

                log.info("子线程被打断后, 继续执行d代码");
            }
        };
        thread.start();
        log.info("子线程的状态 {}", thread.getState());
        //让主线程也休眠2s,这样过了2s后查看子线程状态肯定还是休眠
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这时候再次查看子线程状态【还在休眠中】
        log.info("再次查看子线程的状态 {}", thread.getState());
        //打断线程
        thread.interrupt();

    }

    public static void timeUnit(){
        try {
            //休眠1s
            TimeUnit.SECONDS.sleep(1);
            //休眠1h
            TimeUnit.HOURS.sleep(1);
            //休眠1天
            TimeUnit.DAYS.sleep(1);
            //休眠1min
            TimeUnit.MINUTES.sleep(1);
            //休眠100ms
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
