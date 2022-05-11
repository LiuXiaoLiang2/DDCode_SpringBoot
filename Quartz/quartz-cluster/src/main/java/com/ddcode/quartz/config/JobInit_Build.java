package com.ddcode.quartz.config;

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


    public void init() throws SchedulerException {
        log.info("执行 JobInit_Build");
        //开启三个定时任务
        startJob("jobDetailName1", "triggerName1");
        startJob("jobDetailName2", "triggerName2");
        startJob("jobDetailName3", "triggerName3");
    }


    /**
     * 执行任务
     * @param jobDetailName
     * @param triggerName
     * @throws SchedulerException
     */
    public void startJob(String jobDetailName, String triggerName) throws SchedulerException {
        log.info("执行 JobInit_Build, jobDetailName {} , triggerName {}", jobDetailName, triggerName);
        //创建jobDetail
        JobDetail jobDetail = JobBuilder.newJob(SpringJob1.class).withIdentity(jobDetailName).build();

        //创建触发器
        //立马执行
        Trigger trigger = TriggerBuilder.newTrigger().startNow().withIdentity(triggerName)
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/3 * * * * ? ")
                ).build();

        //由调度器执行
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
