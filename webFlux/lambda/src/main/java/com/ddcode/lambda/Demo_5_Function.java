package com.ddcode.lambda;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 定义一个接口实现金额格式化, 也是一个函数式接口
 */
@FunctionalInterface
interface IMoneyFormat{
    public String format(int money);
}

/**
 * 定义一个金额实体类
 */
@Slf4j(topic = "c.MyMoney")
class MyMoney{
    public int money;

    public MyMoney(int money){
        this.money = money;
    }

    public void print(IMoneyFormat iMoneyFormat){
        log.info("format money {}", iMoneyFormat.format(this.money));
    }
}

/**
 * 定义一个金额实体类
 */
@Slf4j(topic = "c.MyMoneyFunction")
class MyMoneyFunction {
    public int money;

    public MyMoneyFunction(int money){
        this.money = money;
    }

    public void print(Function<Integer, String> iMoneyFormat){
        log.info("format MyMoneyFunction {}", iMoneyFormat.apply(this.money));
    }
}


@Slf4j(topic = "c.Demo_5_Function")
public class Demo_5_Function {

    public static void main(String[] args) {
        //testFunctionAndThen();
//        testFunctionCompose();
//        testPredicate();
        testConsumer();
    }

    public static void testFunction(){
        MyMoney myMoney = new MyMoney(999999999);
        myMoney.print(i->new DecimalFormat("#,###").format(i));

        MyMoneyFunction myMoneyFunction = new MyMoneyFunction(999999999);
        myMoneyFunction.print(i->new DecimalFormat("#,###").format(i));

        Function iMoneyFormat = i -> new DecimalFormat("#,###").format(i);
        Function function = iMoneyFormat.andThen(s -> "人名币: " + s);
        myMoneyFunction.print(function);
    }

    public static void testFunctionAndThen(){
        Function f1 = i -> i + ",f1,";
        Function f2 = i -> i + ",f2,";
        Function f3 = i -> i + ",f3,";
        Function result3 = f1.andThen(f2).andThen(f3);
        log.info("结果 {}",result3.apply(1) );
    }

    public static void testFunctionCompose(){
        Function f1 = i -> i + ",f1,";
        Function f2 = i -> i + ",f2,";
        Function f3 = i -> i + ",f3,";
        Function result3 = f1.compose(f2).compose(f3);
        log.info("结果 {}",result3.apply(1) );
    }

    /**
     * 测试断言
     */
    public static void testPredicate(){
        //指定泛型类型
        Predicate<String> predicate = i->i.equals("lucy");
        boolean lucy = predicate.test("lucy");
        boolean jack = predicate.test("jack");
        System.out.println(lucy);
        System.out.println(jack);;
    }

    /**
     * 测试消费函数
     */
    public static void testConsumer(){

        Consumer<Integer> consumer = i -> System.out.println(i+1);
        consumer.accept(10);
    }
}
