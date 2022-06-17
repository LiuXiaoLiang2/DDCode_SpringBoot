package com.ddcode.java.reentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock的简单使用
 */
@Slf4j(topic = "c.ReentrantReadWriteLock")
public class Demo_1_ReentrantReadWriteLock {

    public static void main(String[] args) {

//        readRead();
//        readWrite();
//        writeWrite();
        DataContainer dataContainer = new DataContainer();
//        dataContainer.readWrite();
        dataContainer.writeRead();
    }

    /**
     * 测试读锁+读锁可以并发
     */
    public static void readRead(){
        DataContainer dataContainer = new DataContainer();
        new Thread("t1"){
            @Override
            public void run() {
                dataContainer.read();
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                dataContainer.read();
            }
        }.start();
    }

    /**
     * 测试读锁+写锁互斥
     */
    public static void readWrite(){
        DataContainer dataContainer = new DataContainer();
        new Thread("t1"){
            @Override
            public void run() {
                dataContainer.read();
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                dataContainer.write();
            }
        }.start();
    }

    /**
     * 测试写锁+写锁互斥
     */
    public static void writeWrite(){
        DataContainer dataContainer = new DataContainer();
        new Thread("t1"){
            @Override
            public void run() {
                dataContainer.write();
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                dataContainer.write();
            }
        }.start();
    }


}

/**
 * 数据容器类
 */
@Slf4j(topic = "c.DataContainer")
class DataContainer{

    private Object data;
    //创建ReentrantReadWriteLock对象
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //创建读锁
    ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    //创建写锁
    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    

    /**
     * 读数据的方法
     * @return
     */
    public Object read(){
        Condition condition = readLock.newCondition();
        try {
            log.info("ThreadName {} 获取读锁", Thread.currentThread().getName());
            readLock.lock();
            try {
                log.info("ThreadName {} 读取数据", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        } finally {
            log.info("ThreadName {} 释放读锁", Thread.currentThread().getName());
            readLock.unlock();
        }

    }

    /**
     * 写数据的方法
     */
    public void write(){
        try {
            log.info("ThreadName {} 获取写锁", Thread.currentThread().getName());
            writeLock.lock();
            try {
                log.info("ThreadName {} 写数据", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            log.info("ThreadName {} 释放写锁", Thread.currentThread().getName());
            writeLock.unlock();
        }
    }

    public void readWrite(){
        try {
            log.info("ThreadName {} 获取读锁", Thread.currentThread().getName());
            readLock.lock();
            try {
                log.info("ThreadName {} 获取写锁", Thread.currentThread().getName());
                writeLock.lock();
            } finally {
                writeLock.unlock();
            }
        } finally {
            readLock.unlock();
        }
    }


    public void writeRead(){
        try {
            log.info("ThreadName {} 获取写锁", Thread.currentThread().getName());
            writeLock.lock();
            try {
                log.info("ThreadName {} 获取读锁", Thread.currentThread().getName());
                readLock.lock();
            } finally {
                log.info("ThreadName {} 释放读锁", Thread.currentThread().getName());
                readLock.unlock();
            }
        } finally {
            log.info("ThreadName {} 释放写锁", Thread.currentThread().getName());
            writeLock.unlock();
        }
    }
}

/**
 * 缓存类
 */
class CacheData{

    Object data;
    // 是否有效，如果失效，需要重新计算 data
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    Object processCachedData() {
        //先获取读锁
        try {
            rwl.readLock().lock();
            if(!cacheValid){
                //如果失效了
                try {
                    //需要用写锁, 使用之前, 必须释放读锁, 否则直接使用写锁, 会造成阻塞
                    rwl.readLock().unlock();
                    //写锁
                    rwl.writeLock().lock();
                    //这里再次判断的原因是:判断是否有其它线程已经获取了写锁、更新了缓存, 避免重复更新
                    if(!cacheValid){
                        data = "1";
                        cacheValid = true;
                    }
                    // 降级为读锁, 释放写锁, 这样能够让其它线程读取缓存
                    rwl.readLock().lock();
                } finally {
                    //释放写锁
                    rwl.writeLock().unlock();
                }
            }
            return data;
        } finally {
            rwl.readLock().unlock();
        }
    }

}