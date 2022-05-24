package com.ddcode.date.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Demo_3_Date_Parse {

    public static void main(String[] args) {

        // 得到当前日期时间
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将日期时间格式化为字符串
        String format = now.format(formatter);
        System.out.println("format = " + format);

        // 将字符串解析为日期时间【支持多线程】
        LocalDateTime parse = LocalDateTime.parse("1985-09-23 10:12:22", formatter);
        System.out.println("parse = " + parse);
    }
}
