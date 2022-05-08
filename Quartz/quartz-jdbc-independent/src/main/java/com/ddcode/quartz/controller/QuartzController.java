package com.ddcode.quartz.controller;

import com.ddcode.quartz.config.QuartzManager;
import com.ddcode.quartz.job.SpringJob1;
import org.quartz.JobDataMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QuartzController {

    @Resource
    private QuartzManager quartzManager;

    @RequestMapping("/createJob")
    public String createJob(){
        quartzManager.createJob("orderJob", "orderJobGroup"
        , "orderTrigger", "orderTriggerGroup", SpringJob1.class,
                "0/3 * * * * ?", new JobDataMap());
        return "ok";
    }

    @RequestMapping("/removeJob")
    public String removeJob(){
        quartzManager.removeJob("orderJob", "orderJobGroup");
        return "ok";
    }

    @RequestMapping("/shutdown")
    public String shutdown(){
        quartzManager.shutdown("orderJob", "orderJobGroup");
        return "ok";
    }

    @RequestMapping("/resumeJob")
    public String resumeJob(){
        quartzManager.resumeJob("orderJob", "orderJobGroup");
        return "ok";
    }
}
