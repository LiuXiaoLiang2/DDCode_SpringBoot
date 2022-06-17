package com.ddcode.java.reentrantReadWriteLock.dao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "c.empDaoCache")
public class EmpDaoCache extends EmpDao{

    private Map<SqlPair, Object> map = new HashMap<>();

    @Override
    public <T> T queryOne(Class<T> clazz, String sql, Object... args) {
        SqlPair sqlPair = new SqlPair(sql, args);
        if(map.containsKey(sqlPair)){
            T t = (T) map.get(sqlPair);
            return t;
        }
        T t = super.queryOne(clazz, sql, args);
        map.put(sqlPair, t);
        return t;
    }


    @Override
    public void update(String sql, Object... args) {
        super.update(sql, args);
        map.clear();
    }
}


