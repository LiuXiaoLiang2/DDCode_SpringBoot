package com.ddcode.interfacePlus.demo;

/**
 * 接口中增加默认方法
 */
interface Demo_1_Interface_Default {

    /**
     * 接口中的抽象方法, 每一个继承该接口的实现类都要实现该方法
     */
    public void test1();

    /**
     * 接口中的默认方法, 实现类可以继承, 也可以不继承
     */
    public default void test2(){
        System.out.println("接口中的默认方法");
    }
}

class Demo_1_Interface_DefaultImpl1 implements Demo_1_Interface_Default {

    @Override
    public void test1() {
        System.out.println("重写父类的抽象方法");
    }
}

class Demo_1_Interface_DefaultImpl2 implements Demo_1_Interface_Default {

    @Override
    public void test1() {
        System.out.println("重写父类的抽象方法");
    }

    @Override
    public void test2() {
        System.out.println("重写父类的默认方法");
    }
}

/**
 * 测试类
 */
class MainTestDefalut {
    public static void main(String[] args) {
        Demo_1_Interface_DefaultImpl1 demo_1_interface_defaultImpl1 = new Demo_1_Interface_DefaultImpl1();
        //直接调用父类的默认方法
        demo_1_interface_defaultImpl1.test2();

        Demo_1_Interface_DefaultImpl2 demo_1_interface_defaultImpl2 = new Demo_1_Interface_DefaultImpl2();
        //直接重写后的默认方法
        demo_1_interface_defaultImpl2.test2();

    }
}
