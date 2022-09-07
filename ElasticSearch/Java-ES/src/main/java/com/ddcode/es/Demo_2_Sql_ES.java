package com.ddcode.es;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Demo_2_Sql_ES {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:es://http://localhost:9200");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(
                    "select * from tvs");
            while (results.next()) {
                System.out.println(results.getString(1));
                System.out.println(results.getString(2));
                System.out.println(results.getString(3));
                System.out.println(results.getString(4));
                System.out.println("============================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
