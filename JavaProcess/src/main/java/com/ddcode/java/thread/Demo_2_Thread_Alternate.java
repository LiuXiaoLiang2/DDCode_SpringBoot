package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试多线程是交替执行
 */
@Slf4j(topic = "c.Thread_Alternate")
public class Demo_2_Thread_Alternate {

    public static void main(String[] args) {

        new Thread(()->{
            for(int i=0; i<100; i++){
                log.info("thread1 执行 i {}" , i);
            }
        }).start();

        new Thread(()->{
            for(int i=0; i<100; i++){
                log.info("thread2 执行 i {}" , i);
            }
        }).start();

    }
}
