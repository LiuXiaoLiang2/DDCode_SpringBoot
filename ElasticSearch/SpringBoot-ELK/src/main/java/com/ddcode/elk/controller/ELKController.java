package com.ddcode.elk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ELKController {

    @RequestMapping("/create/log")
    public void createLog() throws InterruptedException {
        while (true){
            TimeUnit.SECONDS.sleep(1);
            log.info("createLog {}", "ok:" + new Random().nextInt(100));
        }
    }
}
