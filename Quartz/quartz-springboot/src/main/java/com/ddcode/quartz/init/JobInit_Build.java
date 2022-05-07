package com.ddcode.quartz.init;

import com.ddcode.quartz.job.SpringJob1;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 创建方式1：使用build方式创建触发器和JobDetail
 */
@Component
@Slf4j
public class JobInit_Build {

    // 整合了springboot, 调度器可以直接注入进来
    @Resource
    private Scheduler scheduler;

    //该注解的作用: spring初始化后执行, 也就是项目启动后执行
    @PostConstruct
    public void init() throws SchedulerException {
        log.info("执行 JobInit_Build");
        //创建jobDetail
        JobDetail jobDetail = JobBuilder.newJob(SpringJob1.class).build();

        //创建触发器
        //立马执行
        Trigger trigger = TriggerBuilder.newTrigger().startNow().build();

        //由调度器执行
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
