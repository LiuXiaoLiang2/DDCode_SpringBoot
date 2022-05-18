package com.ddcode.lambda.demo;

import com.ddcode.lambda.service.UserService;
import com.ddcode.lambda.service.impl.UserServiceImpl;

public class Demo_02_Lambda_Use_NoParamNoReturn {

    public static void main(String[] args) {
        old();
        old2();
        lambda();

        //lambda局部变量的使用
        UserService userService = ()->{

        };
    }

    /**
     * 创建一个静态方法, 参数是接口
     * @param userInterface
     */
    public static void testNoParamNoReturn(UserService userInterface){
        userInterface.getUser();
    }

    /**
     * 传统写法, 调用上面的静态方法
     */
    public static void old(){
        testNoParamNoReturn(new UserService() {
            @Override
            public void getUser() {
                System.out.println("传统写法1, 匿名内部函数 : 无参, 无返回值");
            }
        });
    }

    /**
     * 传统写法, 实现类方式
     */
    public static void old2() {
        UserServiceImpl userService = new UserServiceImpl();
        testNoParamNoReturn(userService);
    }

    /**
     * lambda表达式
     */
    public static void lambda(){
        testNoParamNoReturn(
            () -> {
                 System.out.println("Lambda : 无参, 无返回值");
            }
        );
    }
}
