package com.ddcode.quartz.job;

import com.ddcode.quartz.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * 整合SpringBoot后，直接继承QuartzJobBean
 * 不需要添加 @Component 注解， 因为JobDetail是交给Quartz管理的,不是交给Spring
 */

@Slf4j
public class SpringJob1 extends QuartzJobBean {

    @Resource
    private HelloService helloService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //执行具体业务代码
        try {
            //集群名称
            String schedulerName = context.getScheduler().getSchedulerName();
            //每个集群中节点实例ID
            String schedulerInstanceId = context.getScheduler().getSchedulerInstanceId();
            log.info("执行定时任务 HelloJob currentThread {} jobDetailName {}, triggerName {}, schedulerName {}, schedulerInstanceId {}", Thread.currentThread().getName(),
                    context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName(), schedulerName, schedulerInstanceId);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
