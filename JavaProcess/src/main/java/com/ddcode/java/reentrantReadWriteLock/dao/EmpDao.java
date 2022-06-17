package com.ddcode.java.reentrantReadWriteLock.dao;

import com.ddcode.java.reentrantReadWriteLock.jdbc.JdbcUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;

@Slf4j(topic = "c.empDao")
public class EmpDao {

    //查询单个数据
    public <T> T queryOne(Class<T> clazz, String sql , Object... args){
        //获取JDBC连接
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JdbcUtil.getConnection();
            // 2.预编译sql语句，返回PreparedStatement的实例
            ps = connection.prepareStatement(sql);
            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 4.执行,并返回结果集
            rs = ps.executeQuery();
            // 获取结果集的元数据: ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过 ResultSetMetaData 获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);
                    // 获取每个列的列名
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                log.info("执行sql {} args {}", sql, args);
                return t;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            JdbcUtil.close(rs,ps,connection);
        }
        return null;
    }


    // 通用的增删改操作: 针对不同的操作（增删改），针对不同的表
    // 无返回值
    public void update(String sql, Object... args) {  // sql中占位符的个数与可变形参的长度相同！
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.获取数据库的连接
            conn = JdbcUtil.getConnection();
            // 2.预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);  // 小心参数声明错误！！
            }
            // 4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.资源的关闭
            JdbcUtil.close(null,ps,conn);
        }
    }

}
