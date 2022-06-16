package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 学习使用thenCompose
 */
@Slf4j(topic = "c.thenCompose")
public class Demo_3_Completable_thenCompose {

    public static void main(String[] args) {
//        thenCompose();
//        thenComposeAsync();
        thenComposeAsyncWithExecutor();
    }

    public static void thenCompose(){
        log.info("----------thenCompose使用-------------");
        log.info("小白:进入餐厅");
        log.info("小白:点了番茄炒鸡蛋 + 一碗米饭");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师:炒菜");
            CommonUtil.sleep(2);
            return "厨师:番茄炒鸡蛋";
        }).thenCompose((result) -> CompletableFuture.supplyAsync(()->{
            log.info("服务员:收到厨师的反馈 {}", result);
            log.info("服务员:打饭");
            CommonUtil.sleep(2);
            return "服务员:整合,番茄炒鸡蛋 + 米饭";
        }));

        //主线程继续执行
        log.info("小白:打王者");
        log.info("小白:阻塞等饭....");
        //异步线程返回的结果
        String result = future.join();
        log.info("小白饭上来了....  result {}" , result);
    }



    public static void thenComposeAsync(){
        log.info("----------thenComposeAsync使用-------------");
        log.info("小白:进入餐厅");
        log.info("小白:点了番茄炒鸡蛋 + 一碗米饭");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师:炒菜");
            CommonUtil.sleep(2);
            return "厨师:番茄炒鸡蛋";
        }).thenComposeAsync((result) -> CompletableFuture.supplyAsync(()->{
            log.info("服务员:收到厨师的反馈 {}", result);
            log.info("服务员:打饭");
            CommonUtil.sleep(2);
            return "服务员:整合,番茄炒鸡蛋 + 米饭";
        }));

        //主线程继续执行
        log.info("小白:打王者");
        log.info("小白:阻塞等饭....");
        //异步线程返回的结果
        String result = future.join();
        log.info("小白饭上来了....  result {}" , result);
    }


    public static void thenComposeAsyncWithExecutor(){
        log.info("----------thenComposeAsyncWithExecutor使用-------------");
        // 创建线程池 , 核心线程数是 2
        ExecutorService executor = Executors.newFixedThreadPool(5);
        log.info("小白:进入餐厅");
        log.info("小白:点了番茄炒鸡蛋 + 一碗米饭");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师:炒菜");
            CommonUtil.sleep(2);
            return "厨师:番茄炒鸡蛋";
        },executor).thenComposeAsync((result) -> CompletableFuture.supplyAsync(()->{
            log.info("服务员:收到厨师的反馈 {}", result);
            log.info("服务员:打饭");
            CommonUtil.sleep(2);
            return "服务员:整合,番茄炒鸡蛋 + 米饭";
        },executor), executor);

        //主线程继续执行
        log.info("小白:打王者");
        log.info("小白:阻塞等饭....");
        //异步线程返回的结果
        String result = future.join();
        log.info("小白饭上来了....  result {}" , result);
    }
}
