package com.ddcode.java.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * future的基本使用
 */
@Slf4j(topic = "c.future")
public class Demo_1_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        //使用callable可以返回返回值
        Callable<String> callable = ()->{
            log.info("进入子线程执行 call 方法");
            //这里要注意 call 方法会抛出异常, 所以这里就不需要try..catch
            //休眠2s
            TimeUnit.SECONDS.sleep(5);
            return "hello callable";
        };

        log.info("将 callable 提交到线程池执行");
        //可以看到有返回值
        Future<String> submit = executorService.submit(callable);


        log.info("主线程继续执行");
        //带超时阻塞等待
        String result = submit.get(2, TimeUnit.SECONDS);
        log.info("主线程阻塞等待子线程 Future 结束 {}", result);
        //关闭线程池
        executorService.shutdown();

    }
}
