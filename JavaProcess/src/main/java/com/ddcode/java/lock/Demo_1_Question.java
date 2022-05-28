package com.ddcode.java.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程使用一个变量的问题
 */
@Slf4j(topic = "c.question")
public class Demo_1_Question {

    public static Integer current = 0;

    public static Object object = new Object();

    public static void main(String[] args) {
//        question();
        synchronizedUse();
    }

    public static void question(){
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.info("t1 线程开始执行");
                for (int i = 0; i < 5000; i++) {
                    current++;
                }
                log.info("t1 线程执行完毕");
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                log.info("t2 线程开始执行");
                for (int i = 0; i < 5000; i++) {
                    current--;
                }
                log.info("t2 线程执行完毕");
            }
        };

        t1.start();
        t2.start();
        try {
            log.info("主线程 开始阻塞");
            t1.join();
            t2.join();
            log.info("主线程 阻塞完毕, current {}", current);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void synchronizedUse(){
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.info("t1 线程开始执行");
                for (int i = 0; i < 5000; i++) {
                    synchronized (object){
                        current++;
                    }

                }
                log.info("t1 线程执行完毕");
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                log.info("t2 线程开始执行");
                for (int i = 0; i < 5000; i++) {
                    synchronized (object){
                        current--;
                    }

                }
                log.info("t2 线程执行完毕");
            }
        };

        t1.start();
        t2.start();
        try {
            log.info("主线程 开始阻塞");
            t1.join();
            t2.join();
            log.info("主线程 阻塞完毕, current {}", current);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
