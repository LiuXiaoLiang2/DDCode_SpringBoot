package com.ddcode.quartz.job.pro;

import com.ddcode.quartz.test.pro.Demo2_Job_Exception_RunNow;
import com.ddcode.quartz.util.DateUtil;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloJobException implements Job {


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Integer num = ++Demo2_Job_Exception_RunNow.count;
        System.out.println("时间 , " + DateUtil.convert(new Date()) + " 第 " + num + ", 次执行定时 start , this.hashcode :" + this.hashCode());
        throw new RuntimeException("故意抛异常");
    }
}
