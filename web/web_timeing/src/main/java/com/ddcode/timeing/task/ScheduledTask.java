package com.ddcode.timeing.task;

import com.ddcode.timeing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledTask {

    @Resource
    private UserService userService;

    /**
     * 延迟执行
     */
    //@Scheduled(fixedDelayString = "2000")
    public void fixedDelayTask() throws InterruptedException {
        log.info("ThreadName {}, 执行任务 {}",  Thread.currentThread().getName(), "fixedDelayTask");
        TimeUnit.SECONDS.sleep(5);
    }

   // @Scheduled(fixedRateString = "2000")
    public void fixedRateTask() throws InterruptedException {
        log.info("ThreadName {}, 执行任务 {}",  Thread.currentThread().getName(), "fixedRateTask");
        TimeUnit.SECONDS.sleep(5);
    }

    @Scheduled(fixedRateString = "2000")
    public void fixedRateTaskPlus() {
        log.info("ThreadName {}, 执行任务 {}, start",  Thread.currentThread().getName(), "fixedRateTaskPlus");
        userService.getUser(Thread.currentThread().getName());
        log.info("ThreadName {}, 执行任务 {}, end",  Thread.currentThread().getName(), "fixedRateTaskPlus");
    }

    //使用配置文件
   // @Scheduled(fixedRateString = "${jobs.fixedRate}")
    public void fixedRateTaskProperties() {
        log.info("ThreadName {}, 执行任务 {}",  Thread.currentThread().getName(), "fixedRateTaskProperties");
    }


    //使用配置文件
    @Scheduled(cron = "${jobs.cron}")
    public void cronTask() {
        log.info("ThreadName {}, 执行任务 {}",  Thread.currentThread().getName(), "cronTask");
    }

}
