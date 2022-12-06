package com.ddcode.generics;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛型的基本使用
 */

class MyGenerics<T>{
    private T key;

    public MyGenerics(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }
}

class MyGenericsPro<T, K, V>{
    private T id;
    private Map<K,V> map;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MyGenericsPro{" +
                "id=" + id +
                ", map=" + map +
                '}';
    }
}

public class Demo_2_Generics_Base {

    public static void main(String[] args) {

        testMyGenericsPro();

    }

    public static void testMyGenerics(){
        //泛型传参是String
        MyGenerics<String> stringMyGenerics = new MyGenerics<>("lucy");
        String stringMyGenericsKey = stringMyGenerics.getKey();
        System.out.println(stringMyGenericsKey);

        //泛型传参是Integer
        MyGenerics<Integer> integerMyGenerics = new MyGenerics<>(1);
        Integer integerMyGenericsKey = integerMyGenerics.getKey();
        System.out.println(integerMyGenericsKey);

        //如果没有指定类型，默认就是object
        MyGenerics objMyGenerics = new MyGenerics<>(true);
        //可以看到返回类型就是object
        Object objMyGenericsKey = objMyGenerics.getKey();
        System.out.println(objMyGenericsKey);

        System.out.println(stringMyGenerics.getClass());
        System.out.println(integerMyGenerics.getClass());
        System.out.println(integerMyGenerics.getClass() == stringMyGenerics.getClass());
    }

    public static void testMyGenericsPro(){
        MyGenericsPro<Integer, String, Double> myGenericsPro = new MyGenericsPro<>();
        myGenericsPro.setId(1);
        Map<String, Double> map = new HashMap<>();
        map.put("lucy", 22.0);
        myGenericsPro.setMap(map);
        System.out.println(myGenericsPro);
    }
}
