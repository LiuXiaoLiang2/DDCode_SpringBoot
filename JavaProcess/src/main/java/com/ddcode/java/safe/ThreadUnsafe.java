package com.ddcode.java.safe;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 成员变量线程安全问题
 */
public class ThreadUnsafe {

    private List<String> list = new Vector<>();

    public void method(int loopNumber){
        for(int i=0; i<loopNumber; i++){
            add();
            remove();
        }
    }

    public void add(){
        list.add("1");
    }

    public void remove(){
        //永远移除第一个元素
        list.remove(0);
    }
}
