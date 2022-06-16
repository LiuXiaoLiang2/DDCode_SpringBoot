package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 使用applyToEither方法
 */
@Slf4j(topic = "c.applyToEither")
public class Demo_5_Completable_applyToEither {

    public static void main(String[] args) {
//        applyToEither();
//        acceptEither();
        runAfterEither();
    }

    public static void applyToEither(){
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
                    return t1 + ", 赶紧上车";
       });

        log.info("张三: 阻塞等待");
        String join = future.join();
        log.info("张三: {}", join);
    }

    public static void acceptEither(){
        log.info("----------acceptEither-------------");
        log.info("张三: 走出餐厅, 来到公交车站");
        log.info("张三: 等待700路公交 或者 800路公交");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("700路公交正在赶来");
            CommonUtil.sleep(2);
            return "700路到了";
        }).acceptEither(
                CompletableFuture.supplyAsync(() -> {
                    log.info("800路公交正在赶来");
                    CommonUtil.sleep(1);
                    return "800路到了";
                }),
                (t1) -> {
                    log.info("最先到的 {}", t1);
                });

        log.info("张三: 阻塞等待");
        Void join = future.join();
        log.info("张三: {}", join);
    }

    public static void runAfterEither(){
        log.info("----------acceptEither-------------");
        log.info("张三: 走出餐厅, 来到公交车站");
        log.info("张三: 等待700路公交 或者 800路公交");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("700路公交正在赶来");
            CommonUtil.sleep(2);
            return "700路到了";
        }).runAfterEither(
                CompletableFuture.supplyAsync(() -> {
                    log.info("800路公交正在赶来");
                    CommonUtil.sleep(1);
                    return "800路到了";
                }),
                () -> {
                    log.info("没有参数, 没有返回值");
                });

        log.info("张三: 阻塞等待");
        Void join = future.join();
        log.info("张三: {}", join);
    }

}
