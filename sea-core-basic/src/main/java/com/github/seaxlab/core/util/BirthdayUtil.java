package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.DateFormatEnum;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * calc birthday util
 *
 * @author spy
 * @version 1.0 2021/5/9
 * @since 1.0
 */
@Slf4j
public final class BirthdayUtil {

    final static String[] constellations = {"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔蝎座", "水瓶座", "双鱼座"};
    final static int[][] constellationDays = {{321, 420}, {421, 521}, {522, 621}, {622, 722}, {723, 823}, {824, 923}, {924, 1023}, {1024, 1122}, {1123, 1221}, {1222, 120}, {121, 219}, {220, 320}};
    final static String[] animalOfTheYear = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    final static String[] yue = {"", "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    //TODO

    /**
     * 获取年龄
     *
     * @param birthday 出生年月日
     * @return
     */
    public static long getAge(Date birthday) {
        if (birthday == null) {
            log.warn("birthday is null");
            return 0;
        }

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(birthday);
            String nowYear = df.format(new Date());

            return Integer.parseInt(nowYear) - Integer.parseInt(year);
        } catch (Exception e) {
            log.error("fail to parse", e);
        }

        return 0;
    }

    /**
     * 获取月份数
     *
     * @param birthday 出生年月日
     * @return
     */
    public static long getMonths(Date birthday) {
        if (birthday == null) {
            log.warn("birthday is null");
            return 0;
        }

        String birthdayStr2 = DateUtil.toString(birthday, DateFormatEnum.yyyy_MM_dd.getValue());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());

        LocalDate begin = LocalDate.parse(birthdayStr2).withDayOfMonth(1);
        LocalDate end = LocalDate.parse(now).withDayOfMonth(1);
        return ChronoUnit.MONTHS.between(begin, end);
    }

    /**
     * 获取天数
     *
     * @param birthday 出生年月日
     * @return
     */
    public static long getDays(Date birthday) {
        if (birthday == null) {
            log.warn("birthday is null");
            return 0;
        }

        String birthdayStr2 = DateUtil.toString(birthday, DateFormatEnum.yyyy_MM_dd.getValue());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());

        return ChronoUnit.DAYS.between(LocalDate.parse(birthdayStr2), LocalDate.parse(now));
    }


    /**
     * 2009-8-21 15:33:54
     * 获得农历的日
     *
     * @param day
     * @return
     */
    public final static String getChinaDate(int day) {
        String a = "";
        if (day == 10) return "初十";
        if (day == 20) return "二十";
        if (day == 30) return "三十";
        int two = (int) ((day) / 10);
        if (two == 0) a = "初";
        if (two == 1) a = "十";
        if (two == 2) a = "廿";
        if (two == 3) a = "三";
        int one = (int) (day % 10);
        switch (one) {
            case 1:
                a += "一";
                break;
            case 2:
                a += "二";
                break;
            case 3:
                a += "三";
                break;
            case 4:
                a += "四";
                break;
            case 5:
                a += "五";
                break;
            case 6:
                a += "六";
                break;
            case 7:
                a += "七";
                break;
            case 8:
                a += "八";
                break;
            case 9:
                a += "九";
                break;
        }
        return a;
    }

    public static int getAge(String birthDay, Calendar c) {
        if (birthDay == null || birthDay.isEmpty() || c == null) {
            return 0;
        }
        int now = c.get(Calendar.YEAR);
        int birthYear = Integer.parseInt(birthDay.substring(0, 4));
        return now - birthYear;
    }

    public static int getAge(int birthYear, Calendar c) {
        int now = c.get(Calendar.YEAR);
        return now - birthYear;
    }

    /**
     * 获取星座
     *
     * @param birthDay 出生日期
     * @return
     */
    public static String getConstellation(String birthDay) {
        int date = Integer.parseInt(birthDay.substring(4));
        return getConstellation(date);
    }

    /**
     * 获取星座
     *
     * @param date
     * @return
     */
    public static String getConstellation(int date) {
        for (int i = 0; i < constellationDays.length; i++) {
            if (date >= constellationDays[i][0] && date <= constellationDays[i][1]) {
                return constellations[i];
            }
        }
        return constellations[9];
    }

    /**
     * 获取星座
     *
     * @param month 月份
     * @param day   天
     * @return
     */
    public static String getConstellation(int month, int day) {
        int date = month * 100 + day;
        return getConstellation(date);
    }

    /**
     * 获取年份生肖，如：龙
     *
     * @param birthDay 出生日期
     * @return 生肖
     */
    public static String getAnimalOfTheYear(String birthDay) {
        return getAnimalOfTheYear(Integer.parseInt(birthDay.substring(0, 4)));
    }

    /**
     * 获取年份生肖，如：龙
     *
     * @param year 年份
     * @return 生肖
     */
    public static String getAnimalOfTheYear(int year) {
        int base = 1984;
        int step = (year - base) % 12;
        if (step < 0) {
            step += 12;
        }
        return animalOfTheYear[step];
    }

    private static String formatDate(int[] date) {
        StringBuffer sb = new StringBuffer();
        sb.append(date[0]);
        sb.append("-");
        sb.append(date[1]);
        sb.append("-");
        sb.append(date[2]);
        return sb.toString();
    }

}
