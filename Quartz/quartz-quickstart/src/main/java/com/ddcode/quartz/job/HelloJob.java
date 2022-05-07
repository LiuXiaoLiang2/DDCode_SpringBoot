package com.ddcode.quartz.job;

import com.ddcode.quartz.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 自定义job
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //执行具体业务代码
        System.out.println("执行定时任务 HelloJob : " + DateUtil.convert(new Date()) + ", currentThread: " +Thread.currentThread().getName());
    }
}
