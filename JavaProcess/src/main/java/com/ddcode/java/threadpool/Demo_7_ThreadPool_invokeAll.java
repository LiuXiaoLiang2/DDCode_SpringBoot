package com.ddcode.java.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用 invokeAll方法
 */
@Slf4j(topic = "c.invokeAll")
public class Demo_7_ThreadPool_invokeAll {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<String>> futureList = executorService.invokeAll(Arrays.asList(
                () -> {
                    log.info("执行任务1");
                    TimeUnit.SECONDS.sleep(1);
                    return "done1";
                },
                () -> {
                    log.info("执行任务2");
                    TimeUnit.SECONDS.sleep(3);
                    return "done2";
                }
                ,
                () -> {
                    log.info("执行任务3");
                    TimeUnit.SECONDS.sleep(1);
                    return "done3";
                }
                ,
                () -> {
                    log.info("执行任务4");
                    TimeUnit.SECONDS.sleep(2);
                    return "done4";
                }
                //加超时时间最多等2s
        ), 2, TimeUnit.SECONDS);

        futureList.forEach((future)->{
            try {
                String result = future.get();
                log.info(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
