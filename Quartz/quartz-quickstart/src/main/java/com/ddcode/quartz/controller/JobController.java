package com.ddcode.quartz.controller;

import com.ddcode.quartz.test.Demo7_Spring;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @RequestMapping("/jobSpring")
    public String jobSpring() throws InterruptedException, SchedulerException {
        Demo7_Spring.jobSpring();
        return "ok";
    }
}
