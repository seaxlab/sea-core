package com.github.spy.sea.core.util;

import com.github.spy.sea.core.enums.RangeModeEnum;
import com.github.spy.sea.core.enums.WeekEnum;
import com.github.spy.sea.core.exception.ExceptionHandler;
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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
     * 获取当前交易日，<font color="red">时间部分为00:00:00</font>
     *
     * @return
     */
    public static Date getBillDate() {
        return truncate(nowDate());
    }

    /**
     * 将制定日期转换成交易日，
     * <font color="red">时间部分为00:00:00</font>
     *
     * @param date
     * @return
     */
    public static Date getBillDate(Date date) {
        return truncate(date);
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

    /**
     * 截取日期
     * 2021-04-12 12:12:12-- HOUR --> 2021-04-12 12:00:00
     *
     * @param date
     * @param field Calendar.xxx DATE/HOUR/SECOND
     * @return
     */
    public static Date truncate(Date date, int field) {
        return DateUtils.truncate(date, field);
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
     * 两个日期时间的差异量,(截断指定单位之后的）
     *
     * @param beginDate 开始
     * @param endDate   结束
     * @param timeUnit  单位
     * @return
     */
    public static Long diffSimple(Date beginDate, Date endDate, ChronoUnit timeUnit) {
        Preconditions.checkNotNull(beginDate, "beginDate不能为空");
        Preconditions.checkNotNull(endDate, "endDate不能为空");
        Preconditions.checkNotNull(timeUnit, "timeUnit不能为空");

        switch (timeUnit) {
            case SECONDS: {
                LocalDateTime l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return ChronoUnit.SECONDS.between(l1, l2);
            }
            case MINUTES: {
                LocalDateTime l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withSecond(0);
                LocalDateTime l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withSecond(0);
                return ChronoUnit.MINUTES.between(l1, l2);
            }
            case HOURS: {
                LocalDateTime l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                                            .withMinute(0)
                                            .withSecond(0);
                LocalDateTime l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                                          .withMinute(0)
                                          .withSecond(0);
                return ChronoUnit.HOURS.between(l1, l2);
            }
            case DAYS: {
                java.time.LocalDate l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.time.LocalDate l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return ChronoUnit.DAYS.between(l1, l2);
            }
            case MONTHS: {
                java.time.LocalDate l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                                  .withDayOfMonth(1);
                java.time.LocalDate l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                                .withDayOfMonth(1);
                return ChronoUnit.MONTHS.between(l1, l2);
            }
            case YEARS: {
                java.time.LocalDate l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.time.LocalDate l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return ChronoUnit.YEARS.between(l1, l2);
            }
            default:
                ExceptionHandler.publishMsg("不支持的时间单位");
                break;
        }

        return 0L;
    }

    /**
     * 两个日期时间的差异量(准确计算）
     *
     * @param beginDate 开始
     * @param endDate   结束
     * @param timeUnit  单位
     * @return
     */
    public static Long diff(Date beginDate, Date endDate, TimeUnit timeUnit) {
        Preconditions.checkNotNull(beginDate, "beginDate不能为空");
        Preconditions.checkNotNull(endDate, "endDate不能为空");
        Preconditions.checkNotNull(timeUnit, "timeUnit不能为空");

        long time = endDate.getTime() - beginDate.getTime();
        switch (timeUnit) {
            case SECONDS:
                return time / 1000;
            case MINUTES:
                return (time / (1000 * 60));
            case HOURS:
                return (time / (1000 * 60 * 60));
            case DAYS:
                return (time / (1000 * 60 * 60 * 24));
        }

        return 0L;
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
     *
     * @param start
     * @param end
     * @return
     */
    public static List<Date> betweenDayList(Date start, Date end) {
        if (start.after(end)) {
            return Lists.newArrayList();
        }
        List<Date> days = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        days.add(start);

        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0) {
                days.add(new Date(calendar.getTime().getTime()));
            } else {
                break;
            }
        }

        return days;
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

        List<String> days = Lists.newArrayList();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        days.add(sdf.format(start));

        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0) {
                days.add(sdf.format(calendar.getTime()));
            } else {
                break;
            }
        }

        return days;
    }

    /**
     * 指定日期范围的天数
     *
     * @param start
     * @param end
     * @param dateFormat
     * @return
     */
    public static List<String> betweenDays(Date start, Date end, String dateFormat) {
        if (start.after(end)) return Lists.newArrayList();

        List<String> days = Lists.newArrayList();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
//        days.add(simpleDateFormat.format(start));
        days.add(dateStr(start, dateFormat));

        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0) {
//                days.add(simpleDateFormat.format(calendar.getTime()));
                days.add(dateStr(calendar.getTime(), dateFormat));
            } else {
                break;
            }
        }

        return days;
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
     * 获取指定日期的星期
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 默认是从1-7，减一0（周日）-6（周六）
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (w == 0) {
            w = 7;
        }
        //1(周一)-7(周日)
        return w;
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
     * <p>
     * beginDate <= targetDate <= endDate
     * </p>
     *
     * @param targetDate 目标日期
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @return
     */
    public static boolean isInRange(Date targetDate, Date beginDate, Date endDate) {
        if (targetDate == null || beginDate == null || endDate == null) {
            log.warn("some one is null");
            return false;
        }

        return beginDate.getTime() <= targetDate.getTime() && targetDate.getTime() <= endDate.getTime();
    }

    /**
     * 判断当前日期时间是否在指定范围内
     *
     * <p>
     * beginDate <=? targetDate <=? endDate
     * </p>
     *
     * @param targetDate 目标日期
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @param rangeMode  边界值 ()、(]、[)、[]
     * @return
     * @see RangeModeEnum
     */
    public static boolean isInRange(Date targetDate, Date beginDate, Date endDate, RangeModeEnum rangeMode) {
        if (targetDate == null || beginDate == null || endDate == null) {
            log.warn("some one is null");
            return false;
        }
        switch (rangeMode) {
            case OPEN_OPEN:
                return beginDate.getTime() < targetDate.getTime() && targetDate.getTime() < endDate.getTime();
            case OPEN_CLOSE:
                return beginDate.getTime() < targetDate.getTime() && targetDate.getTime() <= endDate.getTime();
            case CLOSE_OPEN:
                return beginDate.getTime() <= targetDate.getTime() && targetDate.getTime() < endDate.getTime();
            case CLOSE_CLOSE:
                return beginDate.getTime() <= targetDate.getTime() && targetDate.getTime() <= endDate.getTime();
        }

        return false;
    }


    /**
     * 判断当前时间范围是否在指定范围内
     * <p>
     * totalBeginDate <= targetStartDate<=targetEndDate<= totalEndDate
     * </p>
     *
     * @param targetBeginDate 目标开始日期 （小）
     * @param targetEndDate   目标结束日期
     * @param totalBeginDate  总的开始日期
     * @param totalEndDate    总的结束日期 （大）
     * @return
     */
    public static boolean isInRange(Date targetBeginDate, Date targetEndDate, Date totalBeginDate, Date totalEndDate) {
        if (targetBeginDate == null || targetEndDate == null || totalBeginDate == null || totalEndDate == null) {
            log.warn("some one is null");
            return false;
        }

        boolean flag1 = targetBeginDate.getTime() >= totalBeginDate.getTime();
        boolean flag2 = targetEndDate.getTime() >= targetBeginDate.getTime();
        boolean flag3 = totalEndDate.getTime() >= targetEndDate.getTime();

        return flag1 && flag2 && flag3;
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
        //	|> range(start:2021-04-04T16:00:00.000000000Z, stop:2021-04-05T15:59:59.999000000Z)
        // TODO 最后的999？
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
