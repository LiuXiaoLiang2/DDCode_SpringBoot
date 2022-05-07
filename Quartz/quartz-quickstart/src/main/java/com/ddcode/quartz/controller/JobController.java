package com.ddcode.quartz.controller;

import com.ddcode.quartz.test.Demo8_Spring;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @RequestMapping("/jobSpring")
    public String jobSpring() throws InterruptedException, SchedulerException {
        Demo8_Spring.jobSpring();
        return "ok";
    }
}
