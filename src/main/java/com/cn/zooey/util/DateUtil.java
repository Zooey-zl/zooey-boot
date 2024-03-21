package com.cn.zooey.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Fengzl
 * @date 2024/3/12 16:37
 * @desc 时间工具类
 **/
public class DateUtil {

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(toDateString(date, "yy年M月"));
        System.out.println(toDateTimeString(date, "yy年M月dd日 HH:mm"));

    }

    public static String toDateString(Date date, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        return localDate.format(dateTimeFormatter);
    }

    public static String toDateTimeString(Date date, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime.format(dateTimeFormatter);
    }


}
