package com.ddcode.quartz.job.pro;

import com.ddcode.quartz.test.pro.Demo1_Job_Status;
import com.ddcode.quartz.test.pro.Demo2_Job_Exception_RunNow;
import com.ddcode.quartz.util.DateUtil;
import lombok.SneakyThrows;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.sound.midi.Soundbank;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HelloJobExceptionRunNow implements Job {


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Integer num = ++Demo2_Job_Exception_RunNow.count;
            System.out.println("时间 , "+ DateUtil.convert(new Date()) + " 第 " + num + ", 次执行定时 start , this.hashcode :" + this.hashCode());
            if(num == 1){
                throw new RuntimeException("故意抛异常");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("立马执行一次");
            context.getScheduler().triggerJob(context.getTrigger().getJobKey());

            System.out.println("立马暂停");
            context.getScheduler().pauseJob(context.getTrigger().getJobKey());
        }
    }
}
