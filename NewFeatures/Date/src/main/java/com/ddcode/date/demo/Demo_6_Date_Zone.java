package com.ddcode.date.demo;

import java.time.*;

public class Demo_6_Date_Zone {
    public static void main(String[] args) {
        // 1.获取所有的时区ID
        // ZoneId.getAvailableZoneIds().forEach(System.out::println);


        // 不带时间,获取计算机的当前时间
        LocalDateTime now = LocalDateTime.now(); // 中国使用的东八区的时区.比标准时间早8个小时
        System.out.println("now = " + now);
        // 2.操作带时区的类
        // now(Clock.systemUTC()): 创建世界标准时间
        ZonedDateTime bz = ZonedDateTime.now(Clock.systemUTC());
        System.out.println("bz = " + bz);

        // now(): 使用计算机的默认的时区,创建日期时间
        ZonedDateTime now1 = ZonedDateTime.now();
        System.out.println("now1 = " + now1);

        // 使用指定的时区创建日期时间
        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("America/Vancouver"));
        System.out.println("now2 = " + now2);

        LocalDate localDate = now2.toLocalDate();
        LocalTime localTime = now2.toLocalTime();
        LocalDateTime localDateTime = now2.toLocalDateTime();
    }
}
