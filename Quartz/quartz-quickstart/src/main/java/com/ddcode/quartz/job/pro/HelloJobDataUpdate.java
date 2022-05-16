package com.ddcode.quartz.job.pro;

import com.ddcode.quartz.test.pro.Demo1_JobData_Update;
import com.ddcode.quartz.test.pro.Demo1_Job_Status;
import com.ddcode.quartz.util.DateUtil;
import org.quartz.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 动态修改JobData中的数据
 */
@PersistJobDataAfterExecution
public class HelloJobDataUpdate implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Integer num = ++Demo1_JobData_Update.count;
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String userName = jobDataMap.get("userName").toString();
        System.out.println("时间 , "+ DateUtil.convert(new Date()) + " 第 " + num + ", 次执行定时 userName :" + userName);
        //修改jobData
        jobDataMap.put("userName", userName + (++num));
    }
}
