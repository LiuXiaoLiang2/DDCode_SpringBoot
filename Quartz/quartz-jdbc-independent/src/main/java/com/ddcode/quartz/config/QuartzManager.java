package com.ddcode.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

@Configuration
@Slf4j
public class QuartzManager {

    private final SchedulerFactoryBean schedulerFactoryBean;

    public QuartzManager(SchedulerFactoryBean schedulerFactoryBean) {
        log.info("schedulerFactoryBean {}", schedulerFactoryBean);
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    /**
     * 创建任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param jobClass
     * @param cron
     * @param newJobDataMap
     */
    public void createJob(
            String jobName, String jobGroupName,
            String triggerName, String triggerGroupName,
            Class<? extends Job> jobClass, String cron, JobDataMap newJobDataMap
    ) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                //可以给该JobDetail起一个id
                .withIdentity(jobName, jobGroupName)
                .usingJobData(newJobDataMap)
                .build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        try {
            Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
            log.info("创建job结束 {}", date);
            if (!scheduler.isShutdown()) {
                // 启动
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 珊瑚任务
     * @param name
     * @param group
     * @return
     */
    public boolean removeJob(String name, String group) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            return scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 暂定任务
     * @param name
     * @param group
     */
    public void shutdown(String name, String group) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     * @param name
     * @param group
     */
    public void resumeJob(String name, String group) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public boolean removeJob(String name) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(name);
        try {
            return scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }
}
