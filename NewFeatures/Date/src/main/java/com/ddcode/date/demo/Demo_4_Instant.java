package com.ddcode.date.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Demo_4_Instant {

    public static void main(String[] args) {

        Instant now = Instant.now();
        System.out.println("当前时间戳 = " + now);
        // 获取从1970年1月1日 00:00:00的纳秒
        System.out.println(now.getNano());
        // 获取从1970年1月1日 00:00:00的秒
        System.out.println(now.getEpochSecond());
        // 获取从1970年1月1日 00:00:00的毫秒【其实就是时间戳】
        System.out.println(now.toEpochMilli());
        //使用System获取时间戳
        System.out.println(System.currentTimeMillis());

        //获取过了起始时间【1970年1月1日 00:00:00】5s后的Instant对象
        Instant instant = Instant.ofEpochSecond(5);
        System.out.println(instant);
    }


}
