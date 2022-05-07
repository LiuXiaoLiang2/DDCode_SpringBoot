package com.ddcode.quartz.test;

import com.ddcode.quartz.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class Demo9_quartz_properties {

    /**
     * 定时器的简单入门
     * 一个调度器，一个触发器，一个jobDetail
     * 获取quartz配置文件中的值
     * @param args
     */
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //启动调度器
        scheduler.start();

        String schedulerName = scheduler.getSchedulerName();
        int threadPoolSize = scheduler.getMetaData().getThreadPoolSize();
        System.out.println("schedulerName : " + schedulerName);
        System.out.println("threadPoolSize : " + threadPoolSize);

        //关闭调度器,一旦关闭调度器,整个进程就关了,所以要休眠一会,查看执行效果
        scheduler.shutdown();
    }
}
