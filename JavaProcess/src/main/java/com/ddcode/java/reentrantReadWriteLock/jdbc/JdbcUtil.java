package com.ddcode.java.reentrantReadWriteLock.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

@Slf4j(topic = "c.jdbcUtil")
public class JdbcUtil {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    //将配置文件的读取放在静态代码块中，只要读取一次，随着类的加载而加载
    static {
        try {
            //创建properties集合类   加载并读取配置文件
            Properties pro = new Properties();
            ClassLoader classLoader = JdbcUtil.class.getClassLoader();
            URL url1 = classLoader.getResource("jdbc.properties");
            String path = url1.getPath();
//            System.out.println(path);
            pro.load(new FileReader(path));
            //获取数据 赋值
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");
            //注册驱动
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //获取连接对象
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
    public static void close(Statement stmt, Connection conn){
        //调用下面重载的方法 简化代码
        close(null,stmt,conn);
    }
    //重载方法
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if (resultSet!=null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement!=null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection!=null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
