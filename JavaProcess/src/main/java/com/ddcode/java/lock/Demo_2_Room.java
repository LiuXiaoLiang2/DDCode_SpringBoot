package com.ddcode.java.lock;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.room")
public class Demo_2_Room {

    public static void main(String[] args) {
        useRoom();
    }

    public static void useRoom(){
        Room room = new Room();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.info("t1 线程开始执行");
                for (int i = 0; i < 5000; i++) {
                    room.increment();

                }
                log.info("t1 线程执行完毕");
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                log.info("t2 线程开始执行");
                for (int i = 0; i < 5000; i++) {
                    room.decrement();
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
            log.info("主线程 阻塞完毕, current {}", room.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
