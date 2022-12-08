package com.ddcode.generics;

/**
 * 泛型的类型通配符
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;


public class Demo_7_Generics_Wildcard_Down {


    public static void testWildcardUp(){
        List<DownAnimal> animalList = new ArrayList<>();
        List<DownCat> catArrayList = new ArrayList<>();
        List<DownMiniCat> miniCatList = new ArrayList<>();

        showAnimal(animalList);
        showAnimal(catArrayList);
        //这里报错
        //showAnimal(miniCatList);
    }

    /**
     * 使用的是super下限通配符，传入的泛型类型只能是DownCat或者DownCat的父类
     * @param list
     */
    public static void showAnimal(List<? super DownCat> list){
        list.add(new DownCat());
        list.add(new DownMiniCat());
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
        }
        System.out.println(list);
    }


    public static void testTreeSet(){
        //TreeSet<DownCat> downCats = new TreeSet<>(new MyDownCatComparator());
        TreeSet<DownCat> downCats = new TreeSet<>(new MyDownAnimalComparator());
        //报错：只能是DownCat的父类
        //TreeSet<DownCat> downCats = new TreeSet<DownCat>(new MyDownMiniCatComparator());
        downCats.add(new DownCat("jack", 18));
        downCats.add(new DownCat("aha", 21));
        downCats.add(new DownCat("lucy", 55));
        System.out.println(downCats);
    }

    public static void main(String[] args) {
        testTreeSet();
    }

}


class DownAnimal{

    public String name;

    public DownAnimal() {
    }

    public DownAnimal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DownAnimal{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class DownCat extends DownAnimal{

    public Integer age;

    public DownCat() {
    }

    public DownCat(String name, Integer age) {
        super(name);
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DownCat{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

class DownMiniCat extends DownCat{

    public Integer level;

    public DownMiniCat() {
    }


    public DownMiniCat(String name, Integer age, Integer level) {
        super(name, age);
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "DownMiniCat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", level=" + level +
                '}';
    }
}

class MyDownAnimalComparator implements Comparator<DownAnimal> {

    @Override
    public int compare(DownAnimal o1, DownAnimal o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

class MyDownCatComparator implements Comparator<DownCat> {

    @Override
    public int compare(DownCat o1, DownCat o2) {
        return o1.getAge()-o2.getAge();
    }
}

class MyDownMiniCatComparator implements Comparator<DownMiniCat> {

    @Override
    public int compare(DownMiniCat o1, DownMiniCat o2) {
        return o1.getLevel()-o2.getLevel();
    }
}