package com.ddcode.method;

import lombok.extern.slf4j.Slf4j;

import java.util.function.*;

/**
 * 方法引用
 */
@Slf4j(topic = "c.Demo_1_Method_QuickStart")
public class Demo_1_Method_QuickStart {

    public static int[] ints = {1,2,3,4,5};

    public static void sum(int[] ints){
        int result = 0;
        for (int i = 0; i < ints.length; i++) {
            result = result + ints[i];
        }
        log.info("求和结果 {}", result);
    }


    public static void printTotal(Consumer<int[]> consumer){
        consumer.accept(ints);
    }

    public static void main(String[] args) {
        //普通的lambda表达式写法
        Consumer<int[]> consumer1 = i->sum(i);
        printTotal(consumer1);

        //方法引用的写法
        Consumer<int[]> consumer2 = Demo_1_Method_QuickStart::sum;
        printTotal(consumer2);

        //传统的方式
        NoStaticMethod noStaticMethod = new NoStaticMethod();
        Function<Integer, Integer> function1 = i->noStaticMethod.add(i);
        Integer apply1 = function1.apply(1);
        log.info("apply1 {}", apply1);

        //方法引用的方式, 需要创建对象实例
        Function<Integer, Integer> function2 = noStaticMethod::add;
        Integer apply2 = function2.apply(3);
        log.info("apply2 {}", apply2);

        //使用类名引用非静态方法
        BiFunction<NoStaticMethod, Integer, Integer> biFunction = NoStaticMethod::sub;
        Integer apply3 = biFunction.apply(noStaticMethod, 2);
        log.info("apply3 {}", apply3);

        //使用无参构造
        Supplier<NoStaticMethod> supplier = NoStaticMethod::new;
        NoStaticMethod noStaticMethod1 = supplier.get();

        //使用有参构造
        Function<Integer, NoStaticMethod> function3 = NoStaticMethod::new;
        NoStaticMethod noStaticMethod2 = function3.apply(1);
    }
}

/**
 * 非静态方法引用
 */
class NoStaticMethod{

    int sum = 10;

    public Integer add(Integer param){
        return param + 1;
    }

    /**
     * 默认会把当前实例传入非静态方法，参数名是this，位置是第一个，当我们显示将这个参数写出来后，就可以使用类直接引用非静态方法
     * @param param
     * @return
     */
    public Integer sub(NoStaticMethod this, Integer param){
        return param - 1;
    }

    public NoStaticMethod() {
        System.out.println("无参构造");
    }

    public NoStaticMethod(Integer sum) {
        System.out.println("有参构造");
        this.sum = sum;
    }
}