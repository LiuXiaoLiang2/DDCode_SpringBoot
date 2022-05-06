package com.ddcode.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        log.info("index security");
        return "index security";
    }
}
