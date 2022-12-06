package com.ddcode.generics;

/**
 * 泛型的类型通配符
 */

import java.util.List;

/**
 * 首先定义一个泛型
 * @param <T>
 */
class Box<T> {
    private T first;

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }
}

public class Demo_6_Generics_Wildcard_Up {

    public static void main(String[] args) {

        Box<Number> numberBox = new Box<>();
        numberBox.setFirst(100);
        showBox(numberBox);


        Box<Integer> integerBox = new Box<>();
        integerBox.setFirst(100);
        showBox(integerBox);
    }

    public static void showBox(Box<? extends Number> box){
        Number first = box.getFirst();
        System.out.println(first);
    }




    public static void showAnimal(List<? extends Animal> list){
        System.out.println(list);
    }
}


class Animal{

}
class Cat extends Animal{

}
class MiniCat extends Cat{

}