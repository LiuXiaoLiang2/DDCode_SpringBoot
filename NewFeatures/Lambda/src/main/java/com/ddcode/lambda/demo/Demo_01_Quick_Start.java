package com.ddcode.lambda.demo;

/**
 * 使用匿名内部类存在的问题
 */
public class Demo_01_Quick_Start {
    public static void main(String[] args) {

        //传统写法
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("新线程执行了");
                    }
                }
        ).start();

        //Lambda初体验
        new Thread(
                ()->{
                    System.out.println("Lambda初体验");
                }
        ).start();
    }
}
