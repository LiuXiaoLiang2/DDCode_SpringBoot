package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用 thenApply方法
 */
@Slf4j(topic = "c.thenApply")
public class Demo_2_Completable_thenApply {

    public static void main(String[] args) {
//        thenApply();
//        thenApplyAsync();
//        thenApplyAsyncWithExecutor();
//        thenAcceptAsyncWithExecutor();
        thenRunSyncWithExecutor();
    }

    /**
     * thenApply的同步使用
     */
    public static void thenApply(){
        log.info("小白:吃好了");
        log.info("小白:结账, 开发票");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("服务员:收款500");
            CommonUtil.sleep(1);
            return "服务员:收款500";
        }).thenApply((money) -> {
            log.info("财务:收到服务员给的结果 {}", money);
            CommonUtil.sleep(2);
            return "财务:给500发票";
        });
        log.info("小白:收到朋友打游戏的邀请");
        log.info("小白:阻塞等待发票");
        String join = future.join();
        log.info("小白:等待发票结果 {}", join);
    }

    /**
     * thenApply的异步使用
     */
    public static void thenApplyAsync(){
        log.info("----------------thenApply的异步使用-----------------");
        log.info("小白:吃好了");
        log.info("小白:结账, 开发票");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("服务员:收款500");
            CommonUtil.sleep(1);
            return "服务员:收款500";
        }).thenApplyAsync((money) -> {
            log.info("财务:收到服务员给的结果 {}", money);
            CommonUtil.sleep(2);
            return "财务:给500发票";
        });
        log.info("小白:收到朋友打游戏的邀请");
        log.info("小白:阻塞等待发票");
        String join = future.join();
        log.info("小白:等待发票结果 {}", join);
    }


    /**
     * thenApply的异步使用+线程池
     */
    public static void thenApplyAsyncWithExecutor(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        log.info("----------------thenApply的异步使用+线程池-----------------");
        log.info("小白:吃好了");
        log.info("小白:结账, 开发票");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("服务员:收款500");
            CommonUtil.sleep(1);
            return "服务员:收款500";
        }, executor).thenApplyAsync((money) -> {
            log.info("财务:收到服务员给的结果 {}", money);
            CommonUtil.sleep(2);
            return "财务:给500发票";
        }, executor);
        log.info("小白:收到朋友打游戏的邀请");
        log.info("小白:阻塞等待发票");
        String join = future.join();
        log.info("小白:等待发票结果 {}", join);
    }


    /**
     * thenAccept的异步使用+线程池
     */
    public static void thenAcceptAsyncWithExecutor(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        log.info("----------------thenAccept的异步使用+线程池-----------------");
        log.info("小白:吃好了");
        log.info("小白:结账, 开发票");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("服务员:收款500");
            CommonUtil.sleep(1);
            return "服务员:收款500";
        }, executor).thenAcceptAsync((money) -> {
            log.info("财务:收到服务员给的结果 {}", money);
            CommonUtil.sleep(2);
        }, executor);
        log.info("小白:收到朋友打游戏的邀请");
        log.info("小白:阻塞等待发票");
        Void join = future.join();
        log.info("小白:等待发票结果 {}", join);
    }

    /**
     * thenRun的异步使用+线程池
     */
    public static void thenRunSyncWithExecutor(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        log.info("----------------thenRun的异步使用+线程池-----------------");
        log.info("小白:吃好了");
        log.info("小白:结账, 开发票");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("服务员:收款500");
            CommonUtil.sleep(1);
            return "服务员:收款500";
        }, executor).thenRunAsync(() -> {
            log.info("财务:开发票");
            CommonUtil.sleep(2);
        }, executor);
        log.info("小白:收到朋友打游戏的邀请");
        log.info("小白:阻塞等待发票");
        Void join = future.join();
        log.info("小白:等待发票结果 {}", join);
    }
}
