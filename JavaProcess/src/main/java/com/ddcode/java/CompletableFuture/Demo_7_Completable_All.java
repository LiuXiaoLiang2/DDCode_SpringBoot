package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j(topic = "c.all")
public class Demo_7_Completable_All {

    public static void main(String[] args) {
//        allOf();
        anyOf();
    }

    public static void allOf(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(1);
            return "厨师炒菜";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("服务员打饭");
            CommonUtil.sleep(2);
            return "服务员打饭";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            log.info("下班");
            CommonUtil.sleep(3);
            return "下班";
        });

        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2, future3);

        log.info("返回 {} , {} , {} ", future1.join(), future2.join(), future3.join());
    }


    public static void anyOf(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(1);
            log.info("厨师炒菜, 执行完毕");
            return "厨师炒菜";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("服务员打饭");
            CommonUtil.sleep(2);
            log.info("服务员打饭, 执行完毕");
            return "服务员打饭";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            log.info("下班");
            CommonUtil.sleep(3);
            log.info("下班, 执行完毕");
            return "下班";
        });
        CompletableFuture<Object> future = CompletableFuture.anyOf(future1, future2, future3);
        log.info("future {}", future.join());
    }
}
