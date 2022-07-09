package com.ddcode.java.application;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.SplitListThread")
public class SplitListThread {

    public static List<String> init(){
        List<String> list = Lists.newArrayList();
        int size = 1099;
        for (int i = 0; i < size; i++) {
            list.add("hello-" + i);
        }
        return list;
    }

    public static void main(String[] args) {

        List<String> initList = init();

        // 初始化线程池, 参数一定要一定要一定要调好！！！！
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 50,
                4, TimeUnit.SECONDS, new ArrayBlockingQueue(10), new ThreadPoolExecutor.AbortPolicy());

        // 大集合拆分成N个小集合,
        List<List<String>> splitNList = SplitListUtils.split(initList, 100);
        // 记录单个任务的执行次数
        Integer i =0;
        CountDownLatch countDownLatch = new CountDownLatch(splitNList.size());
        for (List<String> list : splitNList) {
            i++;
            int j = i;
            //每个拆分后的集合都放进去线程池中异步执行
            threadPool.submit(()->{
                log.info("拆分集合 {}, 开始执行", (j + ""));
                list.forEach((str)->{
                    //可以遍历处理业务
                });
                // 任务个数 - 1, 直至为0时唤醒await()
                try {
                    //随机休眠
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                log.info("拆分集合 {}, 执行完毕", (j + ""));
            });
        }

        // 让当前线程处于阻塞状态，直到锁存器计数为零
        try {
            log.info("主线程阻塞等待");
            countDownLatch.await();
            log.info("主线程阻塞等待,完毕, 所有子线程都执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();

    }
}
