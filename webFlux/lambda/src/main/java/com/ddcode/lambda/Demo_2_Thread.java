package com.ddcode.lambda;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Demo_2_Thread")
public class Demo_2_Thread {

    public static void main(String[] args) {
        createThread();
        createThreadLambda();
    }

    /**
     * 传统方式创建线程
     */
    public static void createThread(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("传统方式创建新线程");
            }
        };

        new Thread(runnable).start();
    }

    /**
     * lambda表达式创建线程就不需要内部怎么实现的
     * 只需要知道是无参的，那就使用()
     * 没有返回值，直接输出一句话就可以
     */
    public static void createThreadLambda(){
        Runnable runnable = ()->log.info("lambda方式创建新线程");
        Runnable runnable2 = ()->log.info("lambda方式创建新线程");
        log.info("runnable {}, runnable2 {}", runnable, runnable2);
        new Thread(runnable).start();
    }
}
