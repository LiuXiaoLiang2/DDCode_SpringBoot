package com.ddcode.quartz.test.pro;

import com.ddcode.quartz.job.HelloJob;
import com.ddcode.quartz.job.pro.HelloJobStatus;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * 升级版测试
 *
 */
public class Demo1_Job_Status {

    //统计执行的次数
    public static Integer count = 0;

    /**
     * 一个任务周期执行
     * 当第一次的定时还没有执行完毕
     * 第二次的定时会执行吗
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
        JobDetail jobDetail = JobBuilder.newJob(HelloJobStatus.class).withIdentity("job1", "group1").build();

        //创建触发器
        //需要传入执行策略,也就是后续的cron表达式
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                //执行策略: 每1s执行一次,总共执行2次
                .withSchedule(
                        simpleSchedule().withIntervalInSeconds(1).withRepeatCount(2)
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
