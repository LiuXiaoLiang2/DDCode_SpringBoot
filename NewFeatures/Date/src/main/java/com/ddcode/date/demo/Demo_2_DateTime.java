package com.ddcode.date.demo;

import javax.sound.midi.Soundbank;
import javax.xml.stream.Location;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Demo_2_DateTime {

    public static void main(String[] args) {

        localDateTimeCompare();
    }

    public static void localDate(){

        //创建指定日期
        LocalDate localDate = LocalDate.of(2022, 5, 23);
        System.out.println(localDate);


        //获取当前时间
        LocalDate now = LocalDate.now();
        System.out.println(now);

        //获取年月日
        System.out.println("年 : " + now.getYear());
        System.out.println("月 :" + now.getMonthValue());
        System.out.println("日 :" + now.getDayOfMonth());
        System.out.println("星期 : " + now.getDayOfWeek());
    }

    public static void localtime(){
        //14时15分20秒
        LocalTime localTime = LocalTime.of(14, 15, 20);

        //还可以指定纳秒
        LocalTime localTime1 = LocalTime.of(14, 15, 20, 200);

        //获取当前时分秒
        LocalTime now = LocalTime.now();

        System.out.println("时: " + now.getHour());
        System.out.println("分: " + now.getMinute());
        System.out.println("秒: " + now.getSecond());
        System.out.println("纳秒: " + now.getNano());


    }

    public static void localDateTime(){
        //2022年05月23号 14时29分56秒
        LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 23, 14, 29, 56);

        LocalDateTime now = LocalDateTime.now();

        System.out.println("年 : " + now.getYear());
        System.out.println("月 :" + now.getMonthValue());
        System.out.println("日 :" + now.getDayOfMonth());
        System.out.println("星期 : " + now.getDayOfWeek());
        System.out.println("时: " + now.getHour());
        System.out.println("分: " + now.getMinute());
        System.out.println("秒: " + now.getSecond());
        System.out.println("纳秒: " + now.getNano());

    }

    public static void localDateTimeModify(){
        //2022年05月23号 14时29分56秒

        LocalDateTime now = LocalDateTime.now();
        System.out.println("年 : " + now.getYear());
        System.out.println("月 :" + now.getMonthValue());
        System.out.println("日 :" + now.getDayOfMonth());

        //修改后返回的新的localdatetime对象，之前的对象是不会变的
        LocalDateTime withDayOfYear = now.withYear(2025);
        System.out.println("修改后的年 " + withDayOfYear.getYear());

        //再当前对象的基础上加上或减去指定的时间

        //增加是plus
        LocalDateTime plusMonths = now.plusMonths(5);
        System.out.println("增加后的月 " + plusMonths.getMonthValue());

        //修改是min
        LocalDateTime minusDays = now.minusDays(10);
        System.out.println("减少后的日 " + minusDays.getDayOfMonth());
    }

    public static void localDateTimeCompare(){
        //2022年05月23号 14时29分56秒
        LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 23, 14, 29, 56);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(localDateTime.isAfter(now));
        System.out.println(localDateTime.isBefore(now));

        //将localDateTime转化成localDate和localTime
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();

        LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }
}
