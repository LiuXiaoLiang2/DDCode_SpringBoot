package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j(topic = "c.thenCombine")
public class Demo_4_Completable_thenCombine {

    public static void main(String[] args) {
//        thenCombine();
//        thenAcceptBoth();
        runAfterBoth();
    }

    public static void thenCombine(){
        log.info("----------thenCombine使用-------------");
        log.info("小白进入餐厅");
        log.info("小白点了番茄炒鸡蛋 + 一碗米饭");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(2);
            return "番茄鸡蛋";
        }).thenCombine(
                CompletableFuture.supplyAsync(() -> {
                    log.info("服务员蒸饭");
                    return "饭熟了";
                }),
                (t1, t2) -> {
                    log.info("厨师返回结果 {}", t1);
                    log.info("服务员返回结果 {}", t2);
                    return t1 + ", " + t2 + ", 好了";
                });

        //主线程继续执行
        log.info("小白打王者");
        log.info("小白阻塞等饭....");
        String join = future.join();
        log.info("小白饭上来了....  join {}" , join);
    }


    public static void thenAcceptBoth(){
        log.info("----------thenAcceptBoth使用-------------");
        log.info("小白进入餐厅");
        log.info("小白点了番茄炒鸡蛋 + 一碗米饭");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(2);
            return "番茄鸡蛋";
        }).thenAcceptBoth(
                CompletableFuture.supplyAsync(() -> {
                    log.info("服务员蒸饭");
                    return "饭熟了";
                }),
                (t1, t2) -> {
                    log.info("厨师返回结果 {}", t1);
                    log.info("服务员返回结果 {}", t2);
                    log.info("没有返回值");
                });

        //主线程继续执行
        log.info("小白打王者");
        log.info("小白阻塞等饭....");
        Void join = future.join();
        log.info("小白饭上来了....  join {}" , join);
    }


    public static void runAfterBoth(){
        log.info("----------runAfterBoth使用-------------");
        log.info("小白进入餐厅");
        log.info("小白点了番茄炒鸡蛋 + 一碗米饭");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(2);
            return "番茄鸡蛋";
        }).runAfterBoth(
                CompletableFuture.supplyAsync(() -> {
                    log.info("服务员蒸饭");
                    return "饭熟了";
                }),
                () -> {
                    log.info("没有参数, 没有返回值");
                });

        //主线程继续执行
        log.info("小白打王者");
        log.info("小白阻塞等饭....");
        Void join = future.join();
        log.info("小白饭上来了....  join {}" , join);
    }
}
