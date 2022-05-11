package com.ddcode.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取用户登录信息
 */
@RestController
@Slf4j
public class UserController {

    @RequestMapping("/getUserInfo")
    public String getUserInfo() {
        //获取User对象
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();

        log.info("User :"+ principal);
        log.info("身份 :"+principal.getUsername());
        log.info("凭证 :"+authentication.getCredentials());
        log.info("权限 :"+authentication.getAuthorities());
        return "hello security";
    }


    @RequestMapping("/getUserInfoAsync")
    public String getUserInfoAsync() {
        //开启线程访问
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication = SecurityContextHolder
                                .getContext().getAuthentication();
                        User principal = (User) authentication.getPrincipal();
                        log.info("User :"+ principal);
                        log.info("身份 :"+principal.getUsername());
                        log.info("凭证 :"+authentication.getCredentials());
                        log.info("权限 :"+authentication.getAuthorities());
                    }
                }
        ).start();
        return "hello security";
    }



}
