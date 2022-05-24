package com.ddcode.date.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo_1_QuickStart {

    public static void main(String[] args) {
        old();
    }

    public static void old(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0; i <100; i++){
            new Thread(()->{
                try {
                    Date parse = sdf.parse("2022-05-23");
                    System.out.println(parse);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }
}
