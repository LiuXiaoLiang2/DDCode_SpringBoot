package com.ddcode.quartz.test;

import com.ddcode.quartz.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class Demo2_more_trigger {

    /**
     * 定时器的简单入门
     * 一个调度器，两个触发器，一个jobDetail
     * 一个JobDetail可以被多个触发器触发
     * 
     * @param args
     */
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //启动调度器
        scheduler.start();

        //创建任务组
        //创建的时候,传入实现了Job接口的自定义类HelloJob
        //一个name和group可以确定唯一的一个jobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

        //一个name和group可以确定唯一的一个trigger
        //创建触发器
        //需要传入执行策略,也就是后续的cron表达式
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                //执行策略: 每5s执行, 永远执行
                .withSchedule(
                        simpleSchedule().withIntervalInSeconds(3).repeatForever()
                ).build();

        //一个name和group可以确定唯一的一个trigger
        //创建触发器2
        //需要传入执行策略,也就是后续的cron表达式
        SimpleTrigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .startNow()
                //有时候我们无法获取到job对象,我们可以在创建触发器的时候,指定到jobDetail,就是通过 name 和 group
                .forJob("job1", "group1")
                //执行策略: 每5s执行, 永远执行
                .withSchedule(
                        simpleSchedule().withIntervalInSeconds(1).repeatForever()
                ).build();

        //由调度器执行定时任务
        //传入任务job和触发器
        scheduler.scheduleJob(jobDetail, trigger);
        //由于我们创建的trigger2的时候,就已经指定了jobDetail,所以在调度器在执行任务的时候,就不许指定jobDetail了
        scheduler.scheduleJob(trigger2);
        //休眠20s
        TimeUnit.SECONDS.sleep(3);

        //关闭调度器
        scheduler.shutdown();
    }
}
