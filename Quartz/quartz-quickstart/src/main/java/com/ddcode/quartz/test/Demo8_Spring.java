package com.ddcode.quartz.test;

import com.ddcode.quartz.job.HelloJobDataTrigger;
import com.ddcode.quartz.job.HelloJobSpring;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class Demo8_Spring {

    /**
     * 定时器的简单入门
     * 使用Spring原生方式依赖
     */
    public static void jobSpring() throws SchedulerException, InterruptedException {

        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //启动调度器
        scheduler.start();

        //创建任务组
        //创建的时候,传入实现了Job接口的自定义类HelloJob
        //一个name和group可以确定唯一的一个jobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJobSpring.class)
                //使用useJobData传递参数
                .usingJobData("job-data-key","job-data-value1")
                .withIdentity("job1", "group1").build();

        //一个name和group可以确定唯一的一个trigger
        //创建触发器
        //需要传入执行策略,也就是后续的cron表达式
        //使用了CronScheduleBuilder后就使用Trigger对象接收
        Trigger trigger = TriggerBuilder.newTrigger()
                //使用useJobData传递参数
                .usingJobData("trigger-data-key","trigger-data-value1")
                .withIdentity("trigger1", "group1")
                .startNow()
                //创建cron策略对象
                .withSchedule(
                        //每秒执行1次
                        CronScheduleBuilder.cronSchedule("* * * * * ? *")
                ).build();


        //由调度器执行定时任务
        //传入任务job和触发器
        scheduler.scheduleJob(jobDetail, trigger);
        //休眠20s
        TimeUnit.SECONDS.sleep(3);

        //关闭调度器
        scheduler.shutdown();
    }
}
