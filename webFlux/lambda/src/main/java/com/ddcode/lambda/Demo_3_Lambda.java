package com.ddcode.lambda;

import lombok.extern.slf4j.Slf4j;

/**
 * 定义一个函数式接口
 * FunctionalInterface注解起到约束作用：该接口只能有一个抽象方法
 */
@FunctionalInterface
interface LambdaInterface {
    public int doubleNum(int i);

    default int add(int x, int y){
        return x + y;
    }

    default int sub(int x, int y){
        return x - y;
    }

    default int multiplication(int x, int y){
        return x * y;
    }

    default int pro(int x, int y){
        //我们可以调用
        x = doubleNum(x);
        add(x, y);
        return x * y;
    }
}


@Slf4j(topic = "c.Demo_3_Lambda")
public class Demo_3_Lambda {

    /**
     * 方式4：
     *  1、如果大括号内有且只有一个语句，那么可以省略大括号和return
     *  2、如果只有一个参数，参数类型可以省略，同时小括号也可以省略
     */
    public static void way4(){
        // 方式2
        LambdaInterface lambdaInterface4 = i-> i * 2;;

        int doubleNum4 = lambdaInterface4.doubleNum(1);
        int add = lambdaInterface4.add(1, 2);
        int sub = lambdaInterface4.sub(1, 2);
        int multiplication = lambdaInterface4.multiplication(1, 2);

        log.info("lambdaInterface4....result {}, add {}, sub {}, multiplication {}", doubleNum4, add ,sub, multiplication);

        int pro = lambdaInterface4.pro(1, 2);
        log.info("pro {}", pro);
    }

    public static void main(String[] args) {
        way1();
        way2();
        way3();
        way4();
    }


    /**
     * 最基本的写法
     */
    public static void way1(){
        // 方式1
        LambdaInterface lambdaInterface1 = (int i)->{
            log.info("lambdaInterface1....");
            return i * 2;
        };

        int doubleNum1 = lambdaInterface1.doubleNum(1);
        log.info("lambdaInterface1....result {}", doubleNum1);
    }

    /**
     * 方式2：如果大括号内有且只有一个语句，那么可以省略大括号和return
     */
    public static void way2(){
        // 方式2
        LambdaInterface lambdaInterface2 = (int i)-> i * 2;;

        int doubleNum2 = lambdaInterface2.doubleNum(1);
        log.info("lambdaInterface2....result {}", doubleNum2);
    }

    /**
     * 方式3：
     *  1、如果大括号内有且只有一个语句，那么可以省略大括号和return
     *  2、如果只有一个参数，参数类型可以省略
     */
    public static void way3(){
        // 方式2
        LambdaInterface lambdaInterface3 = (i)-> i * 2;;

        int doubleNum3 = lambdaInterface3.doubleNum(1);
        log.info("lambdaInterface3....result {}", doubleNum3);
    }




}
