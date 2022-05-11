package com.ddcode.quartz.controller;

import com.ddcode.quartz.config.JobInit_Build;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class JobController {

    @Resource
    private JobInit_Build jobInit_build;

    @RequestMapping("/createJob")
    public String createJob() throws SchedulerException {
        jobInit_build.init();
        return "ok";
    }
}
