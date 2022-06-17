package com.ddcode.java.reentrantReadWriteLock.dao;

import lombok.Data;

@Data
class SqlPair {
    private String sql;
    private Object[] args;
    public SqlPair(String sql, Object[] args) {
        this.sql = sql;
        this.args = args;
    }
}
