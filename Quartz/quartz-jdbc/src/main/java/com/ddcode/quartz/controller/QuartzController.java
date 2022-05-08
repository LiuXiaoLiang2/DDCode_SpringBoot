package com.ddcode.quartz.controller;

import com.ddcode.quartz.service.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class QuartzController {

    @Resource
    private ScheduleService scheduleService;

    @RequestMapping("/schedule")
    public String schedule() throws SchedulerException {
        scheduleService.createJob();
        return "ok";
    }
}
