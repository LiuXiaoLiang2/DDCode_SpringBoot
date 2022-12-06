package com.ddcode.generics;

/**
 * 定义泛型接口
 * @param <T>
 */
interface GenericsInterface<T> {
    T getKey();
}

/**
 * 定义接口的实现类, 可以
 * @param <T>
 * @param <E>
 */
class GenericsInterfaceImpl<T, E> implements  GenericsInterface<T>{

    private T key;
    private E value;

    public GenericsInterfaceImpl(T key, E value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public T getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GenericsInterfaceImpl{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

class GenericsInterfaceSecondImpl implements  GenericsInterface<String>{

    @Override
    public String getKey() {
        return "hello world";
    }

}

public class Demo_4_Generics_Interface {

    public static void main(String[] args) {
//        testGenericsInterfaceImpl();
        genericsInterfaceSecond();
    }

    public static void testGenericsInterfaceImpl(){
        GenericsInterfaceImpl<Integer, String> genericsInterface = new GenericsInterfaceImpl(1, "lucy");
        System.out.println(genericsInterface);
    }

    public static void genericsInterfaceSecond(){
        GenericsInterfaceSecondImpl genericsInterfaceSecond = new GenericsInterfaceSecondImpl();
        System.out.println(genericsInterfaceSecond.getKey());
    }
}
