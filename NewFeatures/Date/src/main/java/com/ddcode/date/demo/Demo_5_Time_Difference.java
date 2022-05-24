package com.ddcode.date.demo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class Demo_5_Time_Difference {

    public static void main(String[] args) {
        // Duration计算时间的距离
        LocalTime now = LocalTime.now();
        LocalTime time = LocalTime.of(14, 15, 20);
        Duration duration = Duration.between(time, now);
        System.out.println("相差的天数:" + duration.toDays());
        System.out.println("相差的小时数:" + duration.toHours());
        System.out.println("相差的分钟数:" + duration.toMinutes());
        System.out.println("相差的秒数:" + duration.getSeconds());
        // Period计算日期的距离
        LocalDate nowDate = LocalDate.now();
        LocalDate date = LocalDate.of(1998, 8, 8);
        // 让后面的时间减去前面的时间
        Period period = Period.between(date, nowDate);
        System.out.println("相差的年:" + period.getYears());
        System.out.println("相差的月:" + period.getMonths());
        System.out.println("相差的天:" + period.getDays());
    }
}
