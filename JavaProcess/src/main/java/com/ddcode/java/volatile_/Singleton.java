package com.ddcode.java.volatile_;

/**
 * 单例模式 v1
 */
public class Singleton {

    public static volatile Singleton singleton = null;

    /**
     * 普通版
     * @return
     */
    public static Singleton getSingletonV1(){
        if(singleton != null){
            return singleton;
        }
        singleton = new Singleton();
        return singleton;
    }

    /**
     * 加入sync
     * @return
     */
    public static synchronized Singleton getSingletonV2(){
        if(singleton != null){
            return singleton;
        }
        singleton = new Singleton();
        return singleton;
    }




    /**
     * 加入sync优化
     * @return
     */
    public static  Singleton getSingletonV3(){
        if(singleton != null){
            return singleton;
        }
        synchronized (Singleton.class){
            if(singleton != null){
                return singleton;
            }
            singleton = new Singleton();
            return singleton;
        }
    }

}
