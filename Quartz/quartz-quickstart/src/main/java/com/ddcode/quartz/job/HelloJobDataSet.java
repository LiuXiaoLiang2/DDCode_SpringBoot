package com.ddcode.quartz.job;

import com.ddcode.quartz.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloJobDataSet implements Job {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //执行具体业务代码
        //context 对象可以获取到 触发器的group和name
        System.out.println("执行定时任务 HelloJob : " + DateUtil.convert(new Date()) + ", currentThread: " +Thread.currentThread().getName() + ", 触发器的名称 :"
                + context.getTrigger().getKey().getGroup() + ": " + context.getTrigger().getKey().getName()
                + "name :" + name);
    }
}
