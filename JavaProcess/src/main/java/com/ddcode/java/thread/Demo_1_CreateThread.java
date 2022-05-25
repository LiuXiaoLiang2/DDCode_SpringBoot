package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程
 */
@Slf4j(topic = "c.create_thread")
public class Demo_1_CreateThread {

    public static void main(String[] args) throws Exception {
        //useThread();
        //useRunnable();
        //useRunnableLambda();
        //useFutureTask();
        useFutureTaskLambda();
    }

    /**
     * 创建线程分两步
     * 1、创建线程
     * 2、启动线程
     */
    public static void useThread(){

        //创建线程, 同时给线程起个名字 t1
        Thread thread1 = new Thread("t1") {
            @Override
            public void run() {
               log.info("t1 线程开始执行...");
            }
        };

        //启动线程
        thread1.start();
    }

    public static void useRunnable(){
        //创建任务对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("线程 runnable 开始执行 ...");
            }
        };
        //创价线程对象, 第一个参数: 任务对象, 第二个参数: 任务名称
        Thread thread2 = new Thread(runnable, "t2");

        //执行任务
        thread2.start();
    }

    public static void useRunnableLambda(){
        //创建任务对象
        Runnable runnable = ()->{
            log.info("线程 runnable lambda 开始执行 ...");
        };
        //创价线程对象, 第一个参数: 任务对象, 第二个参数: 任务名称
        Thread thread2 = new Thread(runnable, "t3");
        //执行任务
        thread2.start();

        //更简化
        new Thread(()->{
            log.info("线程 runnable lambda plus 开始执行 ...");
        }, "t3").start();
    }

    public static void useFutureTask() throws ExecutionException, InterruptedException {
        //创建callable对象
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                log.info("线程 useFutureTask 开始执行...");
                //让子线程执行2s
                TimeUnit.SECONDS.sleep(2);
                return 200;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        //创建thread对象
        Thread thread4 = new Thread(futureTask, "t4");
        //开启线程
        thread4.start();

        //如果想要获取线程的返回值,比如上面的 200
        log.info("主线程 main 同步阻塞等待 子线程执行");
        Integer result = futureTask.get();
        log.info("主线程 main 同步阻塞等待结束 子线程返回结果 {}", result);
    }

    public static void useFutureTaskLambda() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(()->{
            log.info("线程 useFutureTaskLambda 开始执行...");
            //让子线程执行2s
            TimeUnit.SECONDS.sleep(2);
            return 200;
        });
        //创建thread对象
        Thread thread4 = new Thread(futureTask, "t4");
        //开启线程
        thread4.start();

        //如果想要获取线程的返回值,比如上面的 200
        log.info("主线程 main 同步阻塞等待 子线程执行");
        Integer result = futureTask.get();
        log.info("主线程 main 同步阻塞等待结束 子线程返回结果 {}", result);
    }

}
