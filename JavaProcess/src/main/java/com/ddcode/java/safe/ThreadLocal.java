package com.ddcode.java.safe;

import java.util.ArrayList;
import java.util.List;

/**
 * 局部变量线程安全问题
 */
public class ThreadLocal {

    public void method(int loopNumber){
        //设置成为局部变量
        List<String> list = new ArrayList<>();
        for(int i=0; i<loopNumber; i++){
            add(list);
            remove(list);
        }
    }

    public void add(List<String> list){
        list.add("1");
    }

    public void remove(List<String> list){
        //永远移除第一个元素
        list.remove(0);
    }

}


//定义一个子类
class ThreadLocalSub extends ThreadLocal {
    @Override
    public void add(List<String> list) {

        //重写了父类的方法, 新开一个线程
        new Thread(){
            @Override
            public void run() {
                list.add("1");
            }
        }.start();

    }
}
