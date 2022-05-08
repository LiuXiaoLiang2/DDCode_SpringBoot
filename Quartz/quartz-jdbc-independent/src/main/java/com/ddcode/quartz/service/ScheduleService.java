package com.ddcode.quartz.service;

import com.ddcode.quartz.job.SpringJob1;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 创建触发器和JobDetail
 */
@Service
@Slf4j
public class ScheduleService {

    // 整合了springboot, 调度器可以直接注入进来
    @Resource
    private Scheduler scheduler;

    public void createJob() throws SchedulerException {
        log.info("执行 JobInit_Build");
        //创建jobDetail
        JobDetail jobDetail = JobBuilder.newJob(SpringJob1.class)
                .withIdentity("job1", "group1")
                .build();

        //创建触发器
        //立马执行
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                //执行策略: 每3s执行, 永远执行
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/3 * * * * ?")
                )
                .build();

        //由调度器执行
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
