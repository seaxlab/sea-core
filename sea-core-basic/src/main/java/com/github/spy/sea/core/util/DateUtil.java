package com.github.spy.sea.core.util;

import com.github.spy.sea.core.enums.WeekEnum;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Date util
 *
 * @author spy
 * @version 1.0 2019-08-20
 * @since 1.0
 */
public final class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);


    /**
     * 默认日期格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String DAY_FORMAT2 = "yyyyMMdd";
    public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";

    public static final String DATETIME_FORMAT2 = "yyyyMMddHHmmssSSS";
    public static final String DATETIME_FORMAT_HUMAN = "yyyyMMdd_HHmmss";

    public static final String OUTPUT_FORMAT = "yyyy年MM月dd日";

    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT2 = "HHmmss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String FORMAT_YMD_HMS = "yyyy年MM月dd日HH时mm分ss秒";


    /**
     * <默认构造函数>
     */
    private DateUtil() {
    }

    /**
     * <字符串转换成日期>
     * <如果转换格式为空，则利用默认格式进行转换操作>
     *
     * @param dateStr 字符串
     * @param format  日期格式
     * @return 日期
     */
    public static Date strDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        return format(dateStr, format);
    }

    public static Date strDate(String str) {
        return strDate(str, null);
    }

    /**
     * 日期转字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String dateStr(Date date, String format) {
        if (null == date) {
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 重新生成一个对象
     *
     * @param date
     * @return
     */
    public static Date dateNew(Date date) {
        return new Date(date.getTime());
    }

    /**
     * 重新生成一个对象
     *
     * @param date
     * @return
     */
    public static Date clone(Date date) {
        return dateNew(date);
    }


    /**
     * 时间戳转换为字符串
     *
     * @param time
     * @return
     */
    public static String timestampStr(Timestamp time) {
        Date date = new Date(time.getTime());
        return dateStr(date, DEFAULT_FORMAT);
    }

    /**
     * 字符串转时间戳 "yyyy-MM-dd HH:mm:ss"
     *
     * @param str
     * @return
     */
    public static Timestamp strTimestamp(String str) {
        Date date = strDate(str, DEFAULT_FORMAT);
        return new Timestamp(date.getTime());
    }

    /**
     * 获取两个日期之间相差天数,包含两个日期当天
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long dateDifDays(Date beginDate, Date endDate) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.clear();
        calendar1.setTime(beginDate);

        calendar2.clear();
        calendar2.setTime(endDate);

        long diffMillis = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
//		long diffSeconds = diffMillis / 1000;
//		long diffMinutes = diffMillis / (60 * 1000);
//		long diffHours = diffMillis / (60 * 60 * 1000);
        long diffDays = diffMillis / (24L * 60 * 60 * 1000);

        return diffDays + 1;
    }

    /**
     * 获取下一天
     *
     * @param date
     * @return
     */
    public static Date getNextDate(Date date) {
        return getDate(date, 1);
    }

    /**
     * 获取下一天，
     * <font color="red">时间部分为00:00:00</font>
     *
     * @param date
     * @return
     */
    public static Date getNextBillDate(Date date) {
        return truncate(getDate(date, 1));
    }

    /**
     * 获取当前时间的下一天，
     * <font color="red">时间部分为00:00:00</font>
     *
     * @return
     */
    public static Date getNextBillDate() {
        return truncate(getNextBillDate(nowDate()));
    }

    /**
     * 获取前一天
     *
     * @param date
     * @return
     */
    public static Date getPreDate(Date date) {
        return getDate(date, -1);
    }

    /**
     * 获取前一天，
     * <font color="red">时间部分为00:00:00</font>
     *
     * @param date
     * @return
     */
    public static Date getPreBillDate(Date date) {
        return truncate(getDate(date, -1));
    }

    /**
     * 获取当前时间的前一天，
     * <font color="red">时间部分为00:00:00</font>
     *
     * @return
     */
    public static Date getPreBillDate() {
        return getPreBillDate(nowDate());
    }


    /**
     * 获取指定日期的n天的日期
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getDate(Date date, int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, n);
        return calendar.getTime();
    }

    public static Date getCurrentDateWithOutTimeStamp() {
        return DateUtil.strDate(DateUtil.dateStr(new Date(), DEFAULT_DATE_FORMAT), DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化到秒 <br />
     * 如果为null,则返回null
     *
     * @param date
     * @return
     */
    public static String toYMDHMS(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式化到日 <br />
     * 如果为null,则返回null
     *
     * @return
     */
    public static String nowStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);

        return sdf.format(new Date());
    }

    /**
     * 今日日期
     *
     * @return
     */
    public static String todayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);

        return sdf.format(new Date());
    }

    public static String toYMD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);

        return sdf.format(date);
    }

    public static String toIntYMD(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT2);

        return sdf.format(date);
    }

    public static String toYMDHMSNoPoint(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);

        return sdf.format(date);
    }


    public static String toHMS(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);

        return sdf.format(date);
    }

    public static String toIntHMS(Date date) {
        if (date == null) {
            return null;
        }

        return format(date, TIME_FORMAT2);
    }

    public static String toString(Date date, String format) {
        if (date == null) {
            return null;
        }

        return format(date, format);
    }

    /**
     * 计算 month个月后的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 计算 day 天后的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 计算hour小时后的时间
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 计算minute分钟后的时间
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 添加增加second之后的时间
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }


    public static Date parseToDateYMDHMS(String date, String time) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        String sformat;
        date = StringUtils.trim(date);
        time = StringUtils.trim(time);

        if (StringUtils.isEmpty(date)) {
            return null;
        }
        if (StringUtils.isEmpty(time)) {
            return null;
        }

        if (date.contains(":")) {
            sformat = DEFAULT_FORMAT;
        } else if (date.contains("-")) {
            sformat = DAY_FORMAT;
        } else {
            sformat = DAY_FORMAT2;
        }

        if (time.contains(":")) {
            sformat = sformat + " " + TIME_FORMAT;
        } else {
            sformat = sformat + " " + TIME_FORMAT2;
        }

        return format(date + " " + time, sformat);
    }

    /**
     * 生成只到天的时间，保留精度到天
     *
     * @param date 日期
     */
    public static Date truncate(Date date) {

        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 是否是相同的日期,yyyy-MM-dd，不包含时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return truncate(date1).getTime() == truncate(date2).getTime();
    }

    /**
     * 是否是相同的日时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDayTime(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.getTime() == date2.getTime();
    }


    /**
     * 转换成日期
     *
     * @param instant
     * @return
     */
    public static Date parse(Instant instant) {
        if (instant == null) {
            return null;
        }

        return new Date(instant.toEpochMilli());
    }

    /**
     * @param sDate 格式 yyyy-MM-dd
     * @return
     */
    public static Date parseToDate(String sDate) {
        if (StringUtils.isBlank(sDate)) {
            return null;
        }
        String sDateTemp = null;
        String sTime = null;
        switch (sDate.split(" ").length) {
            case 1:
                return parseToDate(sDate, sTime);
            case 2:
                sDateTemp = sDate.split(" ")[0];
                sTime = sDate.split(" ")[1];
                return parseToDate(sDateTemp, sTime);
            default:
                return null;

        }
    }


    /**
     * @param sDate 格式 yyyy-MM-dd
     * @param sTime 格式 HH:mm:ss
     * @return
     */
    public static Date parseToDate(String sDate, String sTime) {
        if (StringUtils.isBlank(sDate)) {
            return null;
        }

        if (StringUtils.isBlank(sTime)) {
            sTime = "00:00:00";
        } else {
            if (sTime.lastIndexOf(":") == sTime.length() - 1) {
                sTime = sTime.substring(0, sTime.length() - 1);
            }
            switch (sTime.split(":").length) {
                case 1:
                    sTime = sTime + ":00:00";
                    break;
                case 2:
                    sTime = sTime + ":00";
                    break;
            }

        }

        String strDate = sDate.trim() + " " + sTime.trim();
        return strDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseToDate(String sDate, String sTime, String sDatePattern, String sTimePattern) {
        if (StringUtils.isBlank(sDate)) {
            return null;
        }

        if (StringUtils.isBlank(sTime)) {
            return null;
        }

        String strDate = sDate.trim() + " " + sTime.trim();
        return strDate(strDate, sDatePattern + " " + sTimePattern);
    }


    /**
     * get offset day from today, clear the hour, min, sec, millsec
     */
    public static Date getTodayOffSet(int offSet) {

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, offSet);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date string2Date(String date) {
        Preconditions.checkArgument(date != null && !"".equals(date.trim()), "date不能为空");
        return strDate(date, DAY_FORMAT);
    }

    public static String formatDate2YMD(Date date) {
        return dateStr(date, DAY_FORMAT);
    }


    /**
     * 凌晨
     *
     * @param date
     * @return
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     * 1 返回yyyy-MM-dd 23:59:59日期
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }

    /**
     * start和end之间的日期，包含start和end
     * 例如：
     * start:2017/03/02 12:00:00
     * end :2017/03/04 13:00:00
     * 返回的list为03/02、03/03、03/04
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> betweenDays(Date start, Date end) {
        if (start.after(end)) return Lists.newArrayList();

        List<String> returnBetweentDaysList = Lists.newArrayList();

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

//        returnBetweentDaysList.add(simpleDateFormat.format(start));
        returnBetweentDaysList.add(dateStr(start, DEFAULT_DATE_FORMAT));

        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0) {
//                returnBetweentDaysList.add(simpleDateFormat.format(calendar.getTime()));
                returnBetweentDaysList.add(dateStr(calendar.getTime(), DEFAULT_DATE_FORMAT));
            } else {
                break;
            }
        }

        return returnBetweentDaysList;
    }

    public static List<String> betweenDays(Date start, Date end, String dateFormat) {
        if (start.after(end)) return Lists.newArrayList();

        List<String> returnBetweentDaysList = Lists.newArrayList();

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

//        returnBetweentDaysList.add(simpleDateFormat.format(start));
        returnBetweentDaysList.add(dateStr(start, dateFormat));

        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0) {
//                returnBetweentDaysList.add(simpleDateFormat.format(calendar.getTime()));
                returnBetweentDaysList.add(dateStr(calendar.getTime(), dateFormat));
            } else {
                break;
            }
        }

        return returnBetweentDaysList;
    }


    /**
     * 比较时间必须在N天之前
     *
     * @param beginDate
     * @param endDate
     * @param N
     * @return
     */
    public static boolean compareDay(Date beginDate, Date endDate, Integer N) {
        //获取N天前的时间
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -N);
        Date date = cal.getTime();
        //获取N天前的23:59:59
        Date beforeTwoDay = DateUtil.weeHours(date, 1);
        if (endDate != null) {
            if (beginDate.compareTo(beforeTwoDay) > 0 || endDate.compareTo(beforeTwoDay) > 0 || beginDate.compareTo(endDate) > 0) {
                return false;
            }
        } else {
            if (beginDate.compareTo(beforeTwoDay) > 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * 尝试把一个String按照指定的多个pattern进行转换,转换成功返回结果,失败返回null,如果值为空直接返回null
     *
     * @param value    需要转换为日期的字符串
     * @param patterns 日期pattern数组
     * @return
     */
    public static Date tryStr2Date(String value, String[] patterns) {
        if (patterns == null || patterns.length == 0) {
            throw new IllegalArgumentException("patterns 不能为空");
        }
        Date d = null;
        if (StringUtils.isNotEmpty(value)) {
            for (String p : patterns) {
                try {
                    d = DateUtil.str2Date(value, p);
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return d;
    }

    /**
     * 按指定格式将字符串转换为日期
     *
     * @param dateStr 日期串
     * @param pattern 格式
     * @return 日期
     */
    public static Date str2Date(String dateStr, String pattern) {
        return strDate(dateStr, pattern);
    }

    /**
     * start和end之间的时数，包含start和end
     * 例如：
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> betweenHous(Date start, Date end) {
        if (start.after(end)) return Lists.newArrayList();

        List<String> returnBetweentDaysList = Lists.newArrayList();

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        String formatStr = "yyyy-MM-dd HH";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        returnBetweentDaysList.add(DateUtil.dateStr(start, formatStr));

        while (true) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            if (calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0) {
                returnBetweentDaysList.add(DateUtil.dateStr(calendar.getTime(), formatStr));
            } else {
                break;
            }
        }

        return returnBetweentDaysList;
    }

    /**
     * 获取周
     * 例如：2017-09-11 返回 周一
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String getWeek(String dateStr, String format) {
        if (format == null) {
            format = DEFAULT_DATE_FORMAT;
        }

        Date date = strDate(dateStr, format);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        return WeekEnum.getDescByType(dayOfWeek);
    }

    /**
     * 获取当前月的第一天
     */
    public static Date getFirstDay(String year, String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //当前月第一天
        return cal.getTime();
    }

    /**
     * 获取当前月的最后一天
     */
    public static Date getLastDay(String year, String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        //当前月最后一天
        return cal.getTime();
    }


    /**
     * 获取日历上显示的  第一天
     */
    public static Date getCalendarFirstDay(Date beginDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        while (calendar.get(Calendar.DAY_OF_WEEK) != 2) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        return calendar.getTime();
    }

    /**
     * 获取日历上显示的最后一天
     */
    public static Date getCalendarEndDay(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTime();
    }


    /**
     * 校验日期格式是否合法
     *
     * @param str
     * @param format yyyy-MM-dd,  yyyyMMdd
     * @return
     */
    public static boolean isValidDate(String str, String format) {
        if (StringUtils.isEmpty(format) || StringUtils.isEmpty(str)) {
            return false;
        }
        try {
            Date date = strDate(str, format);
            return str.equals(dateStr(date, format));
        } catch (Exception e) {
            return false;
        }
    }

    public static String now(String format) {
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        return format(nowDate(), format);
    }

    /**
     * 返回当前日期
     *
     * @return
     */
    public static Date nowDate() {
        return new Date();
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static Integer thisYear() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getYear();
    }

    /**
     * 获取一天的开始时间 yyyy-MM-dd 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getDayStartTime(Date date) {
        String str = dateStr(date, DAY_FORMAT) + " 00:00:00";
        return strDate(str, DEFAULT_FORMAT);
    }

    /**
     * 获取一天的结束时间 yyyy-MM-dd 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {
        String str = dateStr(date, DAY_FORMAT) + " 23:59:59";
        return strDate(str, DEFAULT_FORMAT);
    }


    /**
     * 判断当前日期时间是否在指定范围内
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean nowIsInRange(Date beginDate, Date endDate) {
        return isInRange(nowDate(), beginDate, endDate);
    }

    /**
     * 判断当前日期时间是否在指定范围内
     *
     * @param targetDate
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean isInRange(Date targetDate, Date beginDate, Date endDate) {
        if (targetDate == null || beginDate == null || endDate == null) {
            log.warn("some one is null");
            return false;
        }

        return targetDate.getTime() >= beginDate.getTime() && targetDate.getTime() <= endDate.getTime();
    }


    /**
     * 获取当前时间所在周的开始日期和结束日期
     *
     * @return
     */
    public static Date[] getBeginAndEndOfWeek() {
        return getBeginAndEndOfWeek(0);
    }

    /**
     * 获取当前时间所在周的开始日期、结束日期
     *
     * @param weekOffset 周期  0本周，-1上周，-2上上周，1下周，2下下周；依次类推
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getBeginAndEndOfWeek(int weekOffset) {
        return getBeginAndEndOfWeek(new Date(), weekOffset);
    }

    /**
     * 获取指定时间的，周开始日期和结束日期
     *
     * @param date
     * @param weekOffset 0本周，-1上周，-2上上周，1下周，2下下周；依次类推
     * @return
     */
    public static Date[] getBeginAndEndOfWeek(Date date, int weekOffset) {
        DateTime dateTime;
        if (date == null) {
            dateTime = new DateTime();
        } else {
            dateTime = new DateTime(date.getTime());
        }

        LocalDate localDate = new LocalDate(dateTime.plusWeeks(weekOffset));

        localDate = localDate.dayOfWeek().withMinimumValue();
        Date beginDate = localDate.toDate();
        Date endDate = localDate.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 当天的开始时间 如2020-09-11 00:00:00
     *
     * @return
     */
    public static Date getBeginDateTimeOfDay() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        return dateTime.toDate();
    }

    /**
     * 当天的开始时间 如2020-09-11 23:59:59.999
     *
     * @return
     */
    public static Date getEndDateTimeOfDay() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);
        return dateTime.toDate();
    }

    /**
     * 指定日期的开始和结束日期
     *
     * @param date
     * @return
     */
    public static Date[] getBeginAndEndDateTimeOfDay(Date date) {
        DateTime begin = new DateTime(date.getTime());
        begin = begin.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);


        DateTime end = new DateTime(date.getTime());
        end = end.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);
        return new Date[]{begin.toDate(), end.toDate()};
    }


    // --------------------------------private method

    private static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    private static Date format(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("parseException", e);
            return null;
        }
    }
}
