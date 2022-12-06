package com.ddcode.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 泛型方法
 */
class GenericsMethod<T>{

    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public <T> T getProduct(List<T> list){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }


    public static  <T> T getProductStatic(List<T> list){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public static <T,E,K> void printType(T t, E e, K k){
        System.out.println(t + "\t" + t.getClass().getSimpleName());
        System.out.println(e + "\t" + e.getClass().getSimpleName());
        System.out.println(k + "\t" + k.getClass().getSimpleName());
    }

    //可变参数
    public static <E> void printType(E... e){
        for (int i = 0; i < e.length; i++) {
            System.out.println(e[i]);
        }
    }
}


public class Demo_5_Generics_Method {

    public static void main(String[] args) {
        testGenericsMethod();
    }

    public static void testGenericsMethod(){
        GenericsMethod<Integer> genericsMethod = new GenericsMethod();
        genericsMethod.setId(1);

        //在调用泛型方法的时候，指定泛型方法的变量
        List<String> list = new ArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        String product = genericsMethod.getProduct(list);
        System.out.println(product);

        List<Double> list2 = new ArrayList();
        list2.add(1.0);
        list2.add(2.0);
        list2.add(3.0);
        Double product2 = genericsMethod.getProduct(list2);
        System.out.println(product2);

        Double productStatic = GenericsMethod.getProductStatic(list2);
        System.out.println(productStatic);

        GenericsMethod.printType(100, "20", true);

        GenericsMethod.printType(1,2,3,4,5,6);
    }
}
