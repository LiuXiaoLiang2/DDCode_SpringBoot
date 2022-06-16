package com.ddcode.java.fork;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;

@Slf4j(topic = "c.fork")
public class Demo_Fork {

    public static void main(String[] args) {
        //创建forkPool线程池
        ForkJoinPool pool = new ForkJoinPool(10);
        Integer result = pool.invoke(new AddTaskPlus(1, 10));
        log.info("最终结果 {}", result);
    }
}
