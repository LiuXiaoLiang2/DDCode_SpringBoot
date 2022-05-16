package com.ddcode.quartz.job.pro;

import com.ddcode.quartz.test.pro.Demo1_Job_Status;
import com.ddcode.quartz.util.DateUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@DisallowConcurrentExecution
public class HelloJobStatus implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Integer num = ++Demo1_Job_Status.count;

        System.out.println("时间 , "+ DateUtil.convert(new Date()) + " 第 " + num + ", 次执行定时 start , this.hashcode :" + this.hashCode());

        try {
            //休眠2s
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("时间 , "+ DateUtil.convert(new Date()) + " 第 " + num + ", 次执行定时 end, this.hashcode : " + this.hashCode());
    }
}
