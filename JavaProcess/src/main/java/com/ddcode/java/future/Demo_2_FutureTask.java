package com.ddcode.java.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j(topic = "c.futureTask")
public class Demo_2_FutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //烧水的任务
        Callable<String> task1 = ()->{
            log.info("T1:洗水壶....");
            TimeUnit.SECONDS.sleep(1);
            log.info("T1:烧开水....");
            TimeUnit.SECONDS.sleep(5);
            return "T1:开水已经准备好....";
        };

        //泡茶任务
        Callable<String> task2 = ()->{
            log.info("T2:洗茶壶....");
            TimeUnit.SECONDS.sleep(1);
            log.info("T2:洗茶杯....");
            TimeUnit.SECONDS.sleep(1);
            log.info("T2:拿茶叶....");
            TimeUnit.SECONDS.sleep(1);
            return "T2:茶叶拿到了....";
        };

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //将callable封装成futureTask对象
        FutureTask<String> futureTask1 = new FutureTask<>(task1);
        FutureTask<String> futureTask2 = new FutureTask<>(task2);

        //执行任务
        //executorService也接收futureTask对象
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);

        //主线程等待
        log.info("主线程阻塞等待");
        //但是要注意: 一旦executorService接收的futureTask对象, 那么获取返回值就要使用futureTask的get方法
        log.info("子线程返回结果 {}, {}", futureTask1.get(), futureTask2.get());
        log.info("开始泡茶");
        executorService.shutdown();

    }
}
