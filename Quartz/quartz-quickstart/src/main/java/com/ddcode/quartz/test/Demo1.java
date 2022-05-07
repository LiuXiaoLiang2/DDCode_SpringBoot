package com.ddcode.quartz.test;

import com.ddcode.quartz.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class Demo1 {

    /**
     * 定时器的简单入门
     * @param args
     */
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //启动调度器
        scheduler.start();

        //创建任务组
        //创建的时候,传入实现了Job接口的自定义类HelloJob
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

        //创建触发器
        //需要传入执行策略,也就是后续的cron表达式
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                //执行策略: 每5s执行, 永远执行
                .withSchedule(
                        simpleSchedule().withIntervalInSeconds(5).repeatForever()
                ).build();

        //由调度器执行定时任务
        //传入任务job和触发器
        scheduler.scheduleJob(jobDetail, trigger);
        //休眠20s
        TimeUnit.SECONDS.sleep(20);

        //关闭调度器,一旦关闭调度器,整个进程就关了,所以要休眠一会,查看执行效果
        scheduler.shutdown();
    }
}
