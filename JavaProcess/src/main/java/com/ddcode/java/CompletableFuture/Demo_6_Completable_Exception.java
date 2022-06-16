package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j(topic = "c.exception")
public class Demo_6_Completable_Exception {

    public static void main(String[] args) {
//        exceptionally();
//        exceptionallyPlus();
//        handle();
        whenComplete();
    }

    public static void exceptionally(){
        log.info("张三: 走出餐厅, 来到公交车站");
        log.info("张三: 等待700路公交 或者 800路公交");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("700路公交正在赶来");
            CommonUtil.sleep(2);
            return "700路到了";
        }).applyToEither(
            CompletableFuture.supplyAsync(() -> {
                log.info("800路公交正在赶来");
                CommonUtil.sleep(1);
                return "800路到了";
            }),
            (t1) -> {
                log.info("最先到的 {}", t1);
                if(t1.contains("800")){
                    throw new RuntimeException("700路车撞树了....");
                }
                return t1 + ", 赶紧上车";
            }).exceptionally((e)->{
                log.info("抛异常,", e);
                return "等下一辆700吧, 小子";
        });

        log.info("张三: 阻塞等待");
        String join = future.join();
        log.info("张三: {}", join);
    }


    public static void exceptionallyPlus(){
        log.info("张三: 走出餐厅, 来到公交车站");
        log.info("张三: 等待700路公交 或者 800路公交");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("700路公交正在赶来");
            CommonUtil.sleep(2);
            return "700路到了";
        }).exceptionally((e)->{
            log.info("抛异常,", e);
            return "等下一辆700吧, 小子.....";
        }).applyToEither(
                CompletableFuture.supplyAsync(() -> {
                    log.info("800路公交正在赶来");
                    CommonUtil.sleep(1);
                    return "800路到了";
                }),
                (t1) -> {
                    log.info("最先到的 {}", t1);
                    if(t1.contains("800")){
                        throw new RuntimeException("700路车撞树了....");
                    }
                    return t1 + ", 赶紧上车";
                });

        log.info("张三: 阻塞等待");
        String join = future.join();
        log.info("张三: {}", join);
    }


    public static void handle(){
        log.info("张三: 走出餐厅, 来到公交车站");
        log.info("张三: 等待700路公交 或者 800路公交");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("700路公交正在赶来");
            CommonUtil.sleep(2);
            return "700路到了";
        }).applyToEither(
                CompletableFuture.supplyAsync(() -> {
                    log.info("800路公交正在赶来");
                    CommonUtil.sleep(1);
                    return "800路到了";
                }),
                (t1) -> {
                    log.info("最先到的 {}", t1);
                    if(t1.contains("800")){
                        throw new RuntimeException("700路车撞树了....");
                    }
                    return t1 + ", 赶紧上车";
                }).handle((exception, result)->{
                    log.info("进入handle方法");
                    log.info("exception {}", exception);
                    log.info("result {}", result);
                    return "赶紧上车吧";
        });

        log.info("张三: 阻塞等待");
        String join = future.join();
        log.info("张三: {}", join);
    }


    public static void whenComplete(){
        log.info("张三: 走出餐厅, 来到公交车站");
        log.info("张三: 等待700路公交 或者 800路公交");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("700路公交正在赶来");
            CommonUtil.sleep(2);
            return "700路到了";
        }).applyToEither(
                CompletableFuture.supplyAsync(() -> {
                    log.info("800路公交正在赶来");
                    CommonUtil.sleep(1);
                    return "800路到了";
                }),
                (t1) -> {
                    log.info("最先到的 {}", t1);
                    if(t1.contains("800")){
                        throw new RuntimeException("700路车撞树了....");
                    }
                    return t1 + ", 赶紧上车";
                }).whenComplete((exception, result)->{
            log.info("进入handle方法");
            log.info("exception {}", exception);
            log.info("result {}", result);
        });

        log.info("张三: 阻塞等待");
        String join = future.join();
        log.info("张三: {}", join);
    }
}
