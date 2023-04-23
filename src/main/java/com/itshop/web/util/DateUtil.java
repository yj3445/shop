package com.itshop.web.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtil {
    /**
     * Date 转换成 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * Date 转换成 LocalDate
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * LocalDateTime 转换成 Date
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        //获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        //时区的日期和时间
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        //获取时刻
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDate 转换成 Date
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay().atZone(zoneId).toInstant());
    }

    /**
     * LocalDate 转换成 LocalDateTime
     * @param localDate
     * @return
     */
    public static LocalDateTime localDate2LocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay();
    }

    /**
     * LocalDateTime 转换成 LocalDate
     * @param localDateTime
     * @return
     */
    public static LocalDate localDateTime2LocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    /**
     * 计算两个日期之间的月份
     *
     * @param dateBefore
     * @param dateAfter
     * @return
     */
    public static long durationMonths(LocalDate dateBefore, LocalDate dateAfter) {
        System.out.println(dateBefore+"  "+dateAfter);
        if (dateBefore.getDayOfMonth() > 28) {
            dateBefore = dateBefore.minusDays(5);
        } else if (dateAfter.getDayOfMonth() > 28) {
            dateAfter = dateAfter.minusDays(5);
        }
        return ChronoUnit.MONTHS.between(dateBefore, dateAfter);
    }

    /**
     * 得到两个日期之间间隔的季度数
     *
     * @param dateBefore
     * @param dateAfter
     * @return
     */
    public static long durationQuarters(LocalDate dateBefore, LocalDate dateAfter) {
        Month dateBeforeQuarter = dateBefore.getMonth().firstMonthOfQuarter();
        LocalDate dateBeforeFirstDay = LocalDate.of(dateBefore.getYear(), dateBeforeQuarter, 1);

        Month dateAfterQuarter = dateAfter.getMonth().firstMonthOfQuarter();
        LocalDate dateAfterFirstDay = LocalDate.of(dateAfter.getYear(), dateAfterQuarter, 1);
        long result = 0;
        while (dateBeforeFirstDay.isBefore(dateAfterFirstDay)) {
            dateBeforeFirstDay = dateBeforeFirstDay.plusMonths(3);
            result ++;
        }
        return result;
    }

    /**
     * 得到两个日期之间间隔年份
     *
     * @param dateBefore
     * @param dateAfter
     * @return
     */
    public static long durationYears(LocalDate dateBefore, LocalDate dateAfter) {
        LocalDate dateBeforeFirstDay = dateBefore.with(TemporalAdjusters.firstDayOfYear());
        LocalDate dateAfterFirstDay = dateAfter.with(TemporalAdjusters.firstDayOfYear());
        long result = 0;
        while (dateBeforeFirstDay.isBefore(dateAfterFirstDay)) {
            dateBeforeFirstDay = dateBeforeFirstDay.plusYears(1);
            result ++;
        }
        return result;
    }

    /**
     * @Author: umizhang
     * @Title: getStartOrEndDayOfQuarter
     * @Description TODO 獲取本季度的第一天或最後一天
     * @Date: 2019/7/23 13:46
     * @Param: [today, isFirst: true 表示開始時間，false表示結束時間]
     * @return: java.lang.String
     * @Exception:
     */
    public static LocalDate getStartOrEndDayOfQuarter(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
        }
        return resDate;
    }

    public static void main(String[] args) {
//        LocalDate dateBefore = LocalDate.of(2022, 1, 1);
//        LocalDate dateAfter = LocalDate.of(2022, 1, 23);
//        long quarters = durationMonths(dateBefore, dateAfter);
//        System.out.println("quarters: "+quarters);

        LocalDate[][] aa = new LocalDate[5][2];
        aa[0][0] = LocalDate.of(2022, 12, 19);
        aa[0][1] = LocalDate.of(2022, 12, 31);

        aa[1][0] = LocalDate.of(2023, 1, 1);
        aa[1][1] = LocalDate.of(2023, 3, 31);

        aa[2][0] = LocalDate.of(2023, 4, 1);
        aa[2][1] = LocalDate.of(2023, 6, 30);

        aa[3][0] = LocalDate.of(2023, 7, 1);
        aa[3][1] = LocalDate.of(2023, 9, 30);

        aa[4][0] = LocalDate.of(2023, 10, 1);
        aa[4][1] = LocalDate.of(2023, 12, 18);

        long totalDay = ChronoUnit.DAYS.between(aa[0][0], aa[4][1]);
        System.out.println("totalDay:"+totalDay);

        long totalDay1 = 0;
        for (int i = 0; i < aa.length; i++) {
            long days = ChronoUnit.DAYS.between(aa[i][0], aa[i][1]);
            System.out.println(aa[i][0] + " -- " + aa[i][1] + " -- " + days);
            totalDay1 += days;
        }

        System.out.println("totalDay1:"+totalDay);
    }
}
