package com.ddcode.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        log.info("hello security");
        return "hello security";
    }

    @RequestMapping("/successForwardUrl.html")
    public String successForwardUrl(){
        log.info("hello successForwardUrl");
        return "hello successForwardUrl";
    }

    @RequestMapping("/defaultSuccessUrl.html")
    public String defaultSuccessUrl(){
        log.info("hello defaultSuccessUrl");
        return "hello defaultSuccessUrl";
    }

    @RequestMapping("/failureUrl.html")
    public String failureUrl(){
        log.info("hello failureUrl");
        return "hello failureUrl";
    }
}
