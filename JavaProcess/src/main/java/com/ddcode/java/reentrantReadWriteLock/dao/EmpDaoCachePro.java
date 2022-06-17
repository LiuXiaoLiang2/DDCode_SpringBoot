package com.ddcode.java.reentrantReadWriteLock.dao;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j(topic = "c.empDaoCache")
public class EmpDaoCachePro extends EmpDao{

    private Map<SqlPair, Object> map = new HashMap<>();

    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    @Override
    public <T> T queryOne(Class<T> clazz, String sql, Object... args) {
        SqlPair sqlPair;
        try {
            reentrantReadWriteLock.readLock().lock();
            sqlPair = new SqlPair(sql, args);
            if(map.containsKey(sqlPair)){
                T t = (T) map.get(sqlPair);
                return t;
            }
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }


        try {
            reentrantReadWriteLock.writeLock().lock();
            //进行二次判断
            if(map.containsKey(sqlPair)){
                T t = (T) map.get(sqlPair);
                return t;
            }
            T t = super.queryOne(clazz, sql, args);
            map.put(sqlPair, t);
            return t;
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }


    @Override
    public void update(String sql, Object... args) {
        //先执行更新操作,再执行清除
        try {
            //使用写锁
            reentrantReadWriteLock.writeLock().lock();
            super.update(sql, args);
            map.clear();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }
}


