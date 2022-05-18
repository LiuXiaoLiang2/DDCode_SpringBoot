package com.ddcode.lambda.service.impl;

import com.ddcode.lambda.service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public Integer saveOrder(Long userId, String productName) {
        System.out.println("传统写法2, 具体实现类 有参, 有返回值 , userId :" + userId + ", productName :" + productName);
        return 6;
    }
}
