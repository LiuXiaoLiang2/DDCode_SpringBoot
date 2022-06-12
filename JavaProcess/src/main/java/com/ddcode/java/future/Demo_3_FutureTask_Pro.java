package com.ddcode.java.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 升级版
 */
@Slf4j(topic = "c.main")
public class Demo_3_FutureTask_Pro {

    public static void main(String[] args) {

        FutureTask<String> futureTask2 = new FutureTask<>(new T2Task());
        FutureTask<String> futureTask1 = new FutureTask<>(new T1Task(futureTask2));

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        //执行任务
        //executorService也接收futureTask对象
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);
    }
}


//烧水的线程
@Slf4j(topic = "c.FutureTask1")
class T1Task implements Callable<String>{

    //洗茶杯的任务
    FutureTask<String> futureTask2;
    //将洗茶杯的热内通过构造传过来
    public T1Task(FutureTask<String> futureTask2) {
        this.futureTask2 = futureTask2;
    }

    @Override
    public String call() throws Exception {
        log.info("T1:洗水壶....");
        TimeUnit.SECONDS.sleep(1);
        log.info("T1:烧开水....");
        TimeUnit.SECONDS.sleep(5);
        String t2Result = futureTask2.get();

        log.info("T1:拿到T2的结果 {} , 开始泡茶", t2Result);
        return "T1:上茶";
    }
}

//洗茶杯的线程
@Slf4j(topic = "c.FutureTask2")
class T2Task implements Callable<String>{

    @Override
    public String call() throws Exception {
        log.info("T2:洗茶壶....");
        TimeUnit.SECONDS.sleep(1);
        log.info("T2:洗茶杯....");
        TimeUnit.SECONDS.sleep(1);
        log.info("T2:拿茶叶....");
        TimeUnit.SECONDS.sleep(1);
        return "T2:茶叶拿到了....";
    }
}