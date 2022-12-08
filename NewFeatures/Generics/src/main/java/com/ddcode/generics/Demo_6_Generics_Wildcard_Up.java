package com.ddcode.generics;

/**
 * 泛型的类型通配符
 */

import java.util.ArrayList;
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


    public static void testWildcardUp(){
        List<Animal> animalList = new ArrayList<>();
        List<Cat> catArrayList = new ArrayList<>();
        List<MiniCat> miniCatList = new ArrayList<>();

        //报错, 只能传Cat或者Cat的子类
        //showAnimal(animalList);
        showAnimal(catArrayList);
        showAnimal(miniCatList);
    }


    /**
     * 泛型通配符上限，传入的list的类型，只能是Cat或者Cat的子类
     * @param list
     */
    public static void showAnimal(List<? extends Cat> list){
//        list.add(new Animal());
//        list.add(new Cat());
//        list.add(new MiniCat());
        for (int i = 0; i < list.size(); i++) {
            Cat cat = list.get(i);
        }
        System.out.println(list);
    }
}


class Animal{

}
class Cat extends Animal{

}
class MiniCat extends Cat{

}