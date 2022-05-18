package com.ddcode.lambda.demo;

import com.ddcode.lambda.service.OrderService;
import com.ddcode.lambda.service.impl.OrderServiceImpl;

public class Demo_02_Lambda_Use_ParamReturn {

    public static void main(String[] args) {
        old();
        old2();
        lambda();
    }

    /**
     * 创建一个静态方法, 参数是接口
     * @param orderService
     */
    public static void testParamReturn(OrderService orderService){
        Integer iphone = orderService.saveOrder(1L, "iphone");
        System.out.println("返回结果 " + iphone);
    }

    /**
     * 传统写法, 调用上面的静态方法
     */
    public static void old(){
        testParamReturn(new OrderService() {
            @Override
            public Integer saveOrder(Long userId, String productName) {
                System.out.println("传统写法1, 匿名内部类 有参, 有返回值 , userId :" + userId + ", productName :" + productName);
                return 5;
            }
        });
    }

    /**
     * 传统写法, 实现类
     */
    public static void old2(){
        OrderServiceImpl orderService = new OrderServiceImpl();
        testParamReturn(orderService);
    }


    public static void lambda(){
        testParamReturn(
                //lambda表达式
                (Long userId, String productName)-> {
                    System.out.println("Lambda表达式, 有参, 有返回值 , userId :" + userId + ", productName :" + productName);
                    return 7;
                }
        );
    }
}
