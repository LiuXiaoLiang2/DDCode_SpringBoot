package com.ddcode.quartz.job;

import com.ddcode.quartz.service.HelloService;
import com.ddcode.quartz.util.DateUtil;
import com.ddcode.quartz.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
public class HelloJobSpring implements Job {

    /**
     * 直接注入的方式是不能获取Spring中的对象的
     * 原因：JobDetail对象是交给Quartz对象管理的, 每次执行定时任务都会新 new 一个, 所以直接获取是不行
     * 可以使用Spring上下文中获取,编写一个工具类
     */
    @Resource
    private HelloService helloService1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //执行具体业务代码
        //context 对象可以获取到 触发器的group和name
        System.out.println("执行定时任务 HelloJob : " + DateUtil.convert(new Date()) + ", currentThread: " +Thread.currentThread().getName() + ", 触发器的名称 :"
                + context.getTrigger().getKey().getGroup() + ": " + context.getTrigger().getKey().getName());

        HelloService helloService2 = SpringUtils.getBean(HelloService.class);
        log.info("helloService1 {}", helloService1);
        log.info("helloService2 {}", helloService2);
    }
}
