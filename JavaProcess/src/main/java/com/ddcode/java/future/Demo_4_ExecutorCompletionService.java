package com.ddcode.java.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j(topic = "c.Demo_4_ExecutorCompletionService")
public class Demo_4_ExecutorCompletionService {

    public static void main(String[] args) throws Exception {
        //这里只是为了方便，真正项目中不要这样创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //使用ExecutorCompletionService包裹一下线程池对象
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(()->{
            log.info("任务 1 开始执行");
            TimeUnit.SECONDS.sleep(5);
            log.info("任务 1 结束执行");
            return "任务1执行成功";
        });

        completionService.submit(()->{
            log.info("任务 2 开始执行");
            TimeUnit.SECONDS.sleep(2);
            log.info("任务 2 结束执行");
            return "任务2执行成功";
        });


        completionService.submit(()->{
            log.info("任务 3 开始执行");
            TimeUnit.SECONDS.sleep(3);
            log.info("任务 3 结束执行");
            return "任务3执行成功";
        });

        for(int i=0; i<3; i++){
            Future<String> future = completionService.take();
            String result = future.get();
            log.info("返回结果 result {}", result);
        }

        executorService.shutdown();
    }
}
