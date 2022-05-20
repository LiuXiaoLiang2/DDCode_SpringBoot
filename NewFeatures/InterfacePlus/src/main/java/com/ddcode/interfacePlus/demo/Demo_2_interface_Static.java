package com.ddcode.interfacePlus.demo;

/**
 * 接口中静态方法使用
 */
interface Demo_2_interface_Static {

    public static void testStatic(){
        System.out.println("接口中的静态方法");
    }
}

/**
 * 可以看到静态方法不需要实现
 */
class Demo_2_interface_StaticImpl implements Demo_2_interface_Static{

}

class mainTestStatic {
    public static void main(String[] args) {
        //直接接口调用静态方法
        Demo_2_interface_Static.testStatic();
    }
}