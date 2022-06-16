package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建CompletableFuture
 */
@Slf4j(topic = "c.create")
public class Demo_1_Completable_Create {

    public static void main(String[] args) {
//        noResult();
//        noResultWithExecutor();
//        haveResult();
        haveResultWithExecutor();
    }

    //没有返回值, 非线程池方式
    public static void noResult(){
        log.info("----------创建CompletableFuture: 没有返回值, 非线程池方式-------------");
        log.info("小白进入餐厅");
        log.info("小白点了番茄炒鸡蛋 + 一碗米饭");

        //开启初始异步执行任务
        // 参数1就是 Runnable : 无返回值, 所以异步任务也就没有返回值, 使用lambda表达式
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(2);
            log.info("厨师打发");
        });

        //主线程继续执行
        log.info("小白打王者");
        log.info("小白阻塞等饭....");
        Void join = future.join();
        log.info("小白饭上来了....  join {}" , join);
    }

    //没有返回值, 线程池方式
    public static void noResultWithExecutor(){
        log.info("----------创建CompletableFuture: 没有返回值, 线程池方式-------------");
        // 创建线程池 , 核心线程数是 2
        ExecutorService executor = Executors.newFixedThreadPool(2);

        log.info("小白进入餐厅");
        log.info("小白点了番茄炒鸡蛋 + 一碗米饭");

        //开启初始异步执行任务
        // 参数1就是 Runnable : 无返回值, 所以异步任务也就没有返回值, 使用lambda表达式
        // 参数2就是线程池对象
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info("厨师炒菜");
            CommonUtil.sleep(2);
            log.info("厨师打发");
        }, executor);

        //主线程继续执行
        log.info("小白打王者");
        log.info("小白阻塞等饭....");
        Void join = future.join();
        log.info("小白饭上来了....  join {}" , join);
    }


    //有返回值, 非线程池方式
    public static void haveResult(){
        log.info("----------创建CompletableFuture: 有返回值, 非线程池方式-------------");
        log.info("小白:进入餐厅");
        log.info("小白:点了番茄炒鸡蛋 + 一碗米饭");

        //开启初始异步执行任务
        // 参数1就是 Callable : 有返回值, 所以异步任务也就有返回值, 使用lambda表达式
        // 从返回值也可以看出 CompletableFuture<String> , 有String的返回值
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师:炒菜");
            CommonUtil.sleep(2);
            log.info("厨师:打发");
            return "厨师:番茄炒鸡蛋 + 一碗米饭 好了";
        });

        //主线程继续执行
        log.info("小白:打王者");
        log.info("小白:阻塞等饭....");
        //异步线程返回的结果
        String result = future.join();
        log.info("小白饭上来了....  result {}" , result);
    }

    //有返回值, 非线程池方式
    public static void haveResultWithExecutor(){
        log.info("----------创建CompletableFuture: 有返回值, 线程池方式-------------");
        // 创建线程池 , 核心线程数是 2
        ExecutorService executor = Executors.newFixedThreadPool(2);

        log.info("小白:进入餐厅");
        log.info("小白:点了番茄炒鸡蛋 + 一碗米饭");

        //开启初始异步执行任务
        // 参数1就是 Callable : 有返回值, 所以异步任务也就有返回值, 使用lambda表达式
        // 从返回值也可以看出 CompletableFuture<String> , 有String的返回值
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("厨师:炒菜");
            CommonUtil.sleep(2);
            log.info("厨师:打发");
            return "厨师:番茄炒鸡蛋 + 一碗米饭 好了";
        }, executor);

        //主线程继续执行
        log.info("小白:打王者");
        log.info("小白:阻塞等饭....");
        //异步线程返回的结果
        String result = future.join();
        log.info("小白饭上来了....  result {}" , result);
    }
}
