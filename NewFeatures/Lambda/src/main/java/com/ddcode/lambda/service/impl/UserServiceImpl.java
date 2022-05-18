package com.ddcode.lambda.service.impl;

import com.ddcode.lambda.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void getUser() {
        System.out.println("传统写法2, 实现类 : 无参, 无返回值");
    }
}
