package com.ddcode.lambda.service;

public interface OrderService {
    //有参, 有返回值
    public Integer saveOrder(Long userId, String productName);
}
