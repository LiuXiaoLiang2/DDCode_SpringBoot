package com.ddcode.generics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型擦除
 */
public class Demo_8_Generics_Erase {

    public static void main(String[] args) {
        testInfo();
    }


    public static void test(){
        List<String> stringArrayList = new ArrayList<>();
        List<Integer> integerArrayList = new ArrayList<>();

        System.out.println(stringArrayList.getClass().getName());
        System.out.println(integerArrayList.getClass().getName());
        System.out.println(stringArrayList.getClass() == integerArrayList.getClass());
    }


    public static void testErase(){

        Erase<Integer> stringErase = new Erase<>();
        //利用反射获取字节码对象
        Class<? extends Erase> eraseClass = stringErase.getClass();
        //获取所有成员变量字段
        Field[] declaredFields = eraseClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            //打印成员变量的名称和类型
            Field field = declaredFields[i];
            System.out.println(field.getName() + "\t" + field.getType().getSimpleName());
        }
    }

    public static void testInfo(){
        InfoImpl info = new InfoImpl();
        Class<? extends InfoImpl> infoClass = info.getClass();
        Method[] declaredMethods = infoClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method declaredMethod = declaredMethods[i];
            System.out.println(declaredMethod.getName() + "\t" + declaredMethod.getReturnType().getSimpleName());
        }
    }
}

class Erase<T extends Number>{
    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Erase{" +
                "key=" + key +
                '}';
    }

    /**
     * 定义泛型方法
     * @param t
     * @param <T>
     * @return
     */
    public <T extends List> T show(T t){
        return t;
    }
}

/**
 * 定义一个泛型接口
 * @param <T>
 */
interface Info<T>{
    public T getKey(T t);
}

/**
 * 定义一个实现类
 */
class InfoImpl implements Info<String> {

    @Override
    public String getKey(String str) {
        return str;
    }
}