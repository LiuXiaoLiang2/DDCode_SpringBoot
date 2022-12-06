package com.ddcode.generics;

/**
 * 泛型的继承
 */

/**
 * 定义父类
 * @param <T>
 */
class GenericsParent<T> {
    private T name;

    public GenericsParent(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenericsParent{" +
                "name=" + name +
                '}';
    }
}

/**
 * 子类继承父类，同时子类也是泛型，泛型要和父类的一致
 * @param <T>
 */
class GenericsChild<T> extends GenericsParent<T>{

    /*
     * 实现父类的有参构造
     * @param name
     */
    public GenericsChild(T name) {
        super(name);
    }
}

/**
 * 子类继承父类，子类不是泛型，那么就需要指定父类的具体类型，这里是String
 */
class GenericsChildSecond extends GenericsParent<String>{

    /*
    * 实现父类的有参构造, 参数直接就是上面指定的String
    */
    public GenericsChildSecond(String name) {
        super(name);
    }
}




public class Demo_3_Generics_Extend {

    public static void main(String[] args) {
        testGenericsChild();
        testGenericsChildSecond();
    }

    public static void testGenericsChild(){
        GenericsChild<String> stringGenericsChild = new GenericsChild<String>("lucy");
        System.out.println(stringGenericsChild);
    }

    public static void testGenericsChildSecond(){
        GenericsChildSecond genericsChildSecond = new GenericsChildSecond("tom");
        System.out.println(genericsChildSecond);
    }
}
