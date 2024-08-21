package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.enums.DateUnitEnum;
import com.github.seaxlab.core.enums.DurationTimeEnum;
import com.github.seaxlab.core.enums.RangeModeEnum;
import com.github.seaxlab.core.enums.WeekEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
   * <默认构造函数>
   */
  private DateUtil() {
  }


  /**
   * build simple date format.
   *
   * @param dateFormatEnum date format enum
   * @return
   */
  public static SimpleDateFormat getSdf(DateFormatEnum dateFormatEnum) {
    if (dateFormatEnum == null) {
      dateFormatEnum = DateFormatEnum.yyyy_MM_dd_HH_mm_ss;
    }

    return new SimpleDateFormat(dateFormatEnum.getValue());
  }

  //------------------------------toDate begin ------------------------

  /**
   * 创建日期
   *
   * @param year  年
   * @param month 月
   * @param day   日
   * @return Date
   */
  public static Date of(int year, int month, int day) {
    return of(year, month, day, 0, 0, 0);
  }

  /**
   * 创建日期时间
   *
   * @param year   年
   * @param month  月
   * @param day    日
   * @param hour   时
   * @param minute 分
   * @param second 秒
   * @return Date
   */
  public static Date of(int year, int month, int day, int hour, int minute, int second) {
    LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
    return toDate(localDateTime);
  }

  /**
   * create date
   *
   * @param milliseconds
   * @return Date
   */
  public static Date toDate(Long milliseconds) {
    if (Objects.isNull(milliseconds)) {
      log.warn("milliseconds is null");
      return null;
    }
    return new Date(milliseconds);
  }

  /**
   * convert LocalDateTime to Date
   *
   * @param localDateTime local date time
   * @return Date
   */
  public static Date toDate(LocalDateTime localDateTime) {
    return toDate(localDateTime, ZoneId.systemDefault());
  }

  /**
   * convert LocalDateTime to Date
   *
   * @param localDateTime local date time
   * @param zoneId        zone id
   * @return Date
   */
  public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
    return Date.from(localDateTime.atZone(zoneId).toInstant());
  }

  /**
   * 转换成日期
   *
   * @param instant instant
   * @return 新日期
   */
  public static Date toDate(Instant instant) {
    Precondition.checkNotNull(instant, "instant is null");
    return new Date(instant.toEpochMilli());
  }

  /**
   * date str to default date (yyyy-MM-dd HH:mm:ss)
   *
   * @param dateStr date str
   * @return
   */
  public static Date toDate(String dateStr) {
    return toDate(dateStr, DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
  }

  /**
   * date str to java date
   *
   * @param dateStr        date str
   * @param dateFormatEnum date format enum
   * @return
   */
  public static Date toDate(String dateStr, DateFormatEnum dateFormatEnum) {
    Precondition.checkNotEmpty(dateStr, "date str cannot be empty");

    if (dateFormatEnum == null) {
      dateFormatEnum = DateFormatEnum.yyyy_MM_dd_HH_mm_ss;
    }
    return format(dateStr, dateFormatEnum.getValue());
  }

  /**
   * 字符串转换成日期 <如果转换格式为空，则利用默认格式进行转换操作>
   *
   * @param dateStr 字符串
   * @param format  日期格式
   * @return 日期
   */
  public static Date toDate(String dateStr, String format) {
    Precondition.checkNotEmpty(dateStr, "date str cannot be empty");

    if (StringUtil.isEmpty(format)) {
      format = DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue();
    }
    return format(dateStr, format);
  }


  /**
   * merge date and time to datetime.
   *
   * @param date 指定日期
   * @param time 指定时间
   * @return 日期
   */
  public static Date toDateByDateAndTime(Date date, Date time) {
    Precondition.checkNotNull(date, "date cannot be null.");
    Precondition.checkNotNull(time, "time cannot be null.");

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(time);

    set(calendar, calendar2.get(Calendar.HOUR_OF_DAY), calendar2.get(Calendar.MINUTE),  //
      calendar2.get(Calendar.SECOND), calendar2.get(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * @param date 格式 yyyy-MM-dd
   * @param time 格式 HH:mm:ss
   * @return 日期
   */
  public static Date toDateByDateAndTimeStr(String date, String time) {
    Precondition.checkNotNull(date, "date cannot be null.");

    if (StringUtil.isBlank(time)) {
      time = "00:00:00";
    } else {
      if (time.lastIndexOf(":") == time.length() - 1) {
        time = time.substring(0, time.length() - 1);
      }
      switch (time.split(":").length) {
        case 1:
          time = time + ":00:00";
          break;
        case 2:
          time = time + ":00";
          break;
      }
    }

    String strDate = date.trim() + " " + time.trim();
    return toDate(strDate, DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
  }

  /**
   * 解析成日期
   *
   * @param dateStr     日期
   * @param timeStr     时间
   * @param datePattern 日期格式
   * @param timePattern 时间格式
   * @return date
   */
  public static Date toDateByDateAndTimeStr(String dateStr, String timeStr, String datePattern, String timePattern) {
    Precondition.checkNotEmpty(dateStr, "date str cannot be empty");
    Precondition.checkNotEmpty(timeStr, "time str cannot be empty");

    return toDate(dateStr.trim() + " " + timeStr.trim(), datePattern + " " + timePattern);
  }


  /**
   * 尝试把一个String按照指定的多个pattern进行转换,转换成功返回结果,失败返回null,如果值为空直接返回null
   *
   * @param dateStr  需要转换为日期的字符串
   * @param patterns 日期pattern数组
   * @return date
   */
  public static Date toDateByMatch(String dateStr, String[] patterns) {
    Precondition.checkNotEmpty(dateStr, "date str cannot be empty.");
    Precondition.checkStrElementNotBlank(patterns, "patterns 不能为空");

    Date date = null;
    if (StringUtil.isNotEmpty(dateStr)) {
      for (String p : patterns) {
        try {
          date = toDate(dateStr, p);
          break;
        } catch (Exception e) {
          log.error("fail to convert to date");
        }
      }
    }
    return date;
  }
  //--------------------------------toDate End--------------------


  /**
   * 字符串转时间戳 "yyyy-MM-dd HH:mm:ss"
   *
   * @param str timestamp str
   * @return timestamp
   */
  public static Timestamp toTimestamp(String str) {
    Date date = toDate(str, DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    return new Timestamp(date.getTime());
  }

  //--------------------------------toString Begin--------------------

  /**
   * 将日期转成字符串 yyyy-MM-dd HH:mm:ss
   *
   * @param date
   * @return
   */
  public static String toString(Date date) {
    return toString(date, DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
  }

  /**
   * 将日期转成字符串
   *
   * @param date           date
   * @param dateFormatEnum date format enum
   * @return
   */
  public static String toString(Date date, DateFormatEnum dateFormatEnum) {
    if (dateFormatEnum == null) {
      dateFormatEnum = DateFormatEnum.DEFAULT;
    }
    return toString(date, dateFormatEnum.getValue());
  }

  /**
   * 日期转字符串
   *
   * @param date   指定日期
   * @param format 格式
   * @return string
   */
  public static String toString(Date date, String format) {
    Precondition.checkNotNull(date);

    if (StringUtil.isEmpty(format)) {
      format = DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue();
    }
    return format(date, format);
  }

  /**
   * 时间戳转换为字符串
   *
   * @param time timestamp
   * @return string
   */
  public static String toString(Timestamp time) {
    Precondition.checkNotNull(time);
    Date date = new Date(time.getTime());
    return toString(date, DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
  }

  //--------------------------------toString End--------------------

  /**
   * 重新生成一个对象
   *
   * @param date date
   * @return date
   */
  public static Date clone(Date date) {
    Precondition.checkNotNull(date);
    return new Date(date.getTime());
  }

  /**
   * 获取前一天
   *
   * @param date 指定日期
   * @return date
   */
  public static Date getPreDate(Date date) {
    return addDay(date, -1);
  }

  /**
   * 获取下一天
   *
   * @param date 指定日期
   * @return date
   */
  public static Date getNextDate(Date date) {
    return addDay(date, 1);
  }


  /**
   * 获取当前交易日，<font color="red">时间部分为00:00:00</font>
   *
   * @return date
   */
  public static Date getTradingDay() {
    return truncate(nowDate());
  }

  /**
   * 将制定日期转换成交易日，
   * <font color="red">时间部分为00:00:00</font>
   *
   * @param date 指定日期
   * @return date
   */
  public static Date getTradingDay(Date date) {
    return truncate(date);
  }

  /**
   * 获取当前时间的前一交易日，
   * <font color="red">时间部分为00:00:00</font>
   *
   * @return date
   */
  public static Date getPreTradingDay() {
    return getPreTradingDay(nowDate());
  }

  /**
   * 获取前一交易日，
   * <font color="red">时间部分为00:00:00</font>
   *
   * @param date 指定日期
   * @return date
   */
  public static Date getPreTradingDay(Date date) {
    return truncate(addDay(date, -1));
  }


  /**
   * 获取当前时间的下一交易日，
   * <font color="red">时间部分为00:00:00</font>
   *
   * @return date
   */
  public static Date getNextTradingDay() {
    return getNextTradingDay(nowDate());
  }

  /**
   * 获取指定时间的下一交易日，
   * <font color="red">时间部分为00:00:00</font>
   *
   * @param date 指定日期
   * @return date
   */
  public static Date getNextTradingDay(Date date) {
    return truncate(addDay(date, 1));
  }

  //------------------------add

  /**
   * 增加/减少日期
   *
   * @param date
   * @param delta
   * @param dateUnit
   * @return
   */
  public static Date add(Date date, int delta, DateUnitEnum dateUnit) {
    Precondition.checkNotNull(dateUnit);
    //
    switch (dateUnit) {
      case YEAR:
        return addYear(date, delta);
      case MONTH:
        return addMonth(date, delta);
      case WEEK:
        return addWeek(date, delta);
      case DAY:
        return addDay(date, delta);
      case HOUR:
        return addHour(date, delta);
      case MINUTE:
        return addMinute(date, delta);
      case SECOND:
        return addSecond(date, delta);
      default:
        throw new IllegalArgumentException("不支持的类型");
    }
  }

  /**
   * 计算 delta年后的时间
   *
   * @param date  指定日期
   * @param delta 年变量
   * @return 新日期
   */
  public static Date addYear(Date date, int delta) {
    Precondition.checkNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, delta);
    return calendar.getTime();
  }

  /**
   * 计算 month个月后的时间
   *
   * @param date  指定日期
   * @param month 月数
   * @return 新日期
   */
  public static Date addMonth(Date date, int month) {
    Precondition.checkNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, month);
    return calendar.getTime();
  }

  /**
   * 当前日期的N周前后的日期
   *
   * @param date date
   * @param week week
   * @return
   */
  public static Date addWeek(Date date, int week) {
    return addDay(date, 7 * week);
  }

  /**
   * 计算 day 天后的时间
   *
   * @param date 指定日期
   * @param day  天数量
   * @return 新日期
   */
  public static Date addDay(Date date, int day) {
    Precondition.checkNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, day);
    return calendar.getTime();
  }

  /**
   * 计算hour小时后的时间
   *
   * @param date 指定日期
   * @param hour 小时数
   * @return 新日期
   */
  public static Date addHour(Date date, int hour) {
    Precondition.checkNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, hour);
    return calendar.getTime();
  }

  /**
   * 计算minute分钟后的时间
   *
   * @param date   指定日期
   * @param minute 分钟数
   * @return 新日期
   */
  public static Date addMinute(Date date, int minute) {
    Precondition.checkNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, minute);
    return calendar.getTime();
  }

  /**
   * 添加增加second之后的时间
   *
   * @param date   指定日期
   * @param second 秒数
   * @return 新日期
   */
  public static Date addSecond(Date date, int second) {
    Precondition.checkNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.SECOND, second);
    return calendar.getTime();
  }

  /**
   * 获取指定日期时间的年值，yyyy
   *
   * @param date date time
   * @return int
   */
  public static int getYear(Date date) {
    Precondition.checkNotNull(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    return cal.get(Calendar.YEAR);
  }

  /**
   * 获取指定日期时间的月值，MM
   *
   * @param date date time
   * @return int
   */
  public static int getMonth(Date date) {
    Precondition.checkNotNull(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    return cal.get(Calendar.MONTH) + 1;
  }

  /**
   * 获取指定日期时间的日值，dd
   *
   * @param date date time
   * @return int
   */
  public static int getDay(Date date) {
    Precondition.checkNotNull(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    return cal.get(Calendar.DATE);
  }

  /**
   * 获取指定日期时间的小时，HH
   *
   * @param date date time
   * @return int
   */
  public static int getHour(Date date) {
    Precondition.checkNotNull(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    return cal.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * 获取指定日期时间的分钟，mm
   *
   * @param date date time
   * @return int
   */
  public static int getMinute(Date date) {
    Precondition.checkNotNull(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    return cal.get(Calendar.MINUTE);
  }

  /**
   * 获取指定日期时间的秒值，ss
   *
   * @param date date time
   * @return int
   */
  public static int getSecond(Date date) {
    Precondition.checkNotNull(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    return cal.get(Calendar.SECOND);
  }

  /**
   * 获取指定日期时间的毫秒值
   *
   * @param date
   * @return
   */
  public static int getMilliSecond(Date date) {
    Precondition.checkNotNull(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.MILLISECOND);
  }

  // --------------------------set begin------------------------

  /**
   * 设置指定 year
   *
   * @param date  -
   * @param delta -
   * @return -
   */
  public static Date setYear(Date date, int delta) {
    return set(date, Calendar.YEAR, delta);
  }

  /**
   * 设置指定 month
   *
   * @param date  -
   * @param delta -
   * @return -
   */
  public static Date setMonth(Date date, int delta) {
    return set(date, Calendar.MONTH, delta - 1);
  }

  /**
   * 设置指定 day
   *
   * @param date  -
   * @param delta -
   * @return -
   */
  public static Date setDay(Date date, int delta) {
    return set(date, Calendar.DAY_OF_MONTH, delta);

  }

  /**
   * 设置指定 hour
   *
   * @param date  -
   * @param delta -
   * @return -
   */
  public static Date setHour(Date date, int delta) {
    return set(date, Calendar.HOUR_OF_DAY, delta);
  }

  /**
   * 设置指定minute
   *
   * @param date  -
   * @param delta -
   * @return -
   */
  public static Date setMinute(Date date, int delta) {
    return set(date, Calendar.MINUTE, delta);
  }

  /**
   * 设置成指定秒
   *
   * @param date  -
   * @param delta -
   * @return date
   */
  public static Date setSecond(Date date, int delta) {
    return set(date, Calendar.SECOND, delta);
  }

  /**
   * 设置指定 millis
   *
   * @param date  -
   * @param delta -
   * @return -
   */
  public static Date setMillisSecond(Date date, int delta) {
    return set(date, Calendar.MILLISECOND, delta);
  }

  //----------------------------set end -----------------------------

  /**
   * 生成只到天的时间，保留精度到天
   *
   * @param date 日期
   */
  public static Date truncate(Date date) {
    return DateUtils.truncate(date, Calendar.DATE);
  }

  /**
   * 截取日期 2021-04-12 12:12:12-- HOUR --> 2021-04-12 12:00:00
   *
   * @param date  指定日期
   * @param field Calendar.xxx DATE/HOUR/SECOND
   * @return date
   */
  public static Date truncate(Date date, int field) {
    return DateUtils.truncate(date, field);
  }


  /**
   * 是否是相同的日期,yyyy-MM-dd，不包含时间
   *
   * @param date1 日期1
   * @param date2 日期2
   * @return boolean
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
   * @param date1 日期1
   * @param date2 日期2
   * @return boolean
   */
  public static boolean isSameDayTime(Date date1, Date date2) {
    if (date1 == null || date2 == null) {
      return false;
    }
    return date1.getTime() == date2.getTime();
  }


  /**
   * 获取两个日期之间相差天数,包含两个日期当天,
   *
   * @param beginDate 开始日期
   * @param endDate   结束日期
   * @return long
   */
  public static long diffDays(Date beginDate, Date endDate) {
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
   * 两个日期时间的差异量,(截断指定单位之后的）
   *
   * @param beginDate 开始
   * @param endDate   结束
   * @param timeUnit  单位
   * @return long
   */
  public static long diffSimple(Date beginDate, Date endDate, ChronoUnit timeUnit) {
    Precondition.checkNotNull(beginDate, "beginDate不能为空");
    Precondition.checkNotNull(endDate, "endDate不能为空");
    Precondition.checkNotNull(timeUnit, "timeUnit不能为空");

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
        LocalDateTime l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withMinute(0)
                                    .withSecond(0);
        LocalDateTime l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withMinute(0)
                                  .withSecond(0);
        return ChronoUnit.HOURS.between(l1, l2);
      }
      case DAYS: {
        java.time.LocalDate l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(l1, l2);
      }
      case MONTHS: {
        java.time.LocalDate l1 = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1);
        java.time.LocalDate l2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1);
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
   * @return long
   */
  public static long diff(Date beginDate, Date endDate, TimeUnit timeUnit) {
    Precondition.checkNotNull(beginDate, "beginDate不能为空");
    Precondition.checkNotNull(endDate, "endDate不能为空");
    Precondition.checkNotNull(timeUnit, "timeUnit不能为空");

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
   * start和end之间的日期，包含start和end
   *
   * @param start 开始日期
   * @param end   结束日期
   * @return List date
   */
  public static List<Date> betweenDayList(Date start, Date end) {
    Precondition.checkNotNull(start, "start date cannot be null");
    Precondition.checkNotNull(end, "end date cannot be null");

    if (start.after(end)) {
      log.warn("start date is after end date, plz check.");
      return new ArrayList();
    }
    List<Date> days = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(start);
    setBeginTime(calendar);

    days.add(start);
    while (true) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      if (calendar.getTime().compareTo(end) <= 0) {
        days.add(new Date(calendar.getTime().getTime()));
      } else {
        break;
      }
    }

    return days;
  }

  /**
   * start和end之间的日期，包含start和end 例如： start:2017/03/02 12:00:00 end :2017/03/04 13:00:00 返回的list为03/02、03/03、03/04
   *
   * @param start 开始日期时间
   * @param end   结束日期时间
   * @return list string
   */
  public static List<String> betweenDays(Date start, Date end) {
    return betweenDays(start, end, DateFormatEnum.yyyy_MM_dd.getValue());
  }

  /**
   * 指定日期范围的天数
   *
   * @param start      开始日期
   * @param end        结束日期
   * @param dateFormat 日期格式
   * @return list string
   */
  public static List<String> betweenDays(Date start, Date end, String dateFormat) {
    Precondition.checkNotNull(start, "start date cannot be null");
    Precondition.checkNotNull(end, "end date cannot be null");

    if (start.after(end)) {
      log.warn("start date is after end date, plz check.");
      return new ArrayList();
    }

    List<String> days = new ArrayList();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(start);
    setBeginTime(calendar);
    //
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    days.add(sdf.format(start));

    while (true) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      if (calendar.getTime().compareTo(end) <= 0) {
        days.add(sdf.format(calendar.getTime()));
      } else {
        break;
      }
    }

    return days;
  }

  /**
   * 只比较日期部分
   *
   * @param date1 date
   * @param date2 date
   * @return 1大于，0相等，-1小于
   */
  public static int compareDatePart(Date date1, Date date2) {
    Precondition.checkNotNull(date1);
    Precondition.checkNotNull(date2);

//        DateTimeComparator comparator = DateTimeComparator.getDateOnlyInstance();
//        return comparator.compare(date1, date2);

    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date1);
    setBeginTime(calendar1);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(date2);
    setBeginTime(calendar2);

    return calendar1.getTime().compareTo(calendar2.getTime());
  }

  /**
   * 只比较时间部分
   *
   * @param date1 date
   * @param date2 date
   * @return 1大于，0相等，-1小于
   */
  public static int compareTimePart(Date date1, Date date2) {
    Precondition.checkNotNull(date1);
    Precondition.checkNotNull(date2);

//        DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();
//        return comparator.compare(date1, date2);

    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date1);
    setBeginDate(calendar1);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(date2);
    setBeginDate(calendar2);

    return calendar1.getTime().compareTo(calendar2.getTime());
  }

  /**
   * start和end之间的时数，包含start和end 例如： 2022-1-2 10:10:00 - 2022-1-2 13:00:00
   * <li>2022-1-2 10</li>
   * <li>2022-1-2 11</li>
   * <li>2022-1-2 12</li>
   * <li>2022-1-2 13</li>
   *
   * @param start 开始日期
   * @param end   结束日期
   * @return list string
   */
  public static List<String> betweenHours(Date start, Date end) {
    Precondition.checkNotNull(start, "start date cannot be null");
    Precondition.checkNotNull(end, "end date cannot be null");

    if (start.after(end)) {
      log.warn("start date is after end date, plz check.");
      return new ArrayList();
    }

    List<String> hours = new ArrayList();
    SimpleDateFormat sdf = getSdf(DateFormatEnum.yyyy_MM_dd_HH);

    hours.add(sdf.format(start));

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(start);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    while (true) {
      calendar.add(Calendar.HOUR_OF_DAY, 1);
      if (calendar.getTime().compareTo(end) <= 0) {
        hours.add(sdf.format(calendar.getTime()));
      } else {
        break;
      }
    }

    return hours;
  }

  /**
   * 获取周 例如：2017-09-11 返回 周一
   *
   * @param dateStr 日期字符串
   * @param format  格式
   * @return 日期字符串
   */
  public static String getWeek(String dateStr, String format) {
    Precondition.checkNotBlank(dateStr, "date string cannot be empty.");
    if (format == null) {
      format = DateFormatEnum.yyyy_MM_dd.getValue();
    }

    Date date = toDate(dateStr, format);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

    return WeekEnum.getDescByType(dayOfWeek);
  }

  /**
   * 获取指定日期的星期
   *
   * @param date 日期
   * @return int
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
   *
   * @param year  年
   * @param month 月
   * @return date
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
   *
   * @param year  年
   * @param month 月
   * @return date
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
   *
   * @param beginDate 日期
   * @return date
   */
  public static Date getCalendarFirstDay(Date beginDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(beginDate);
    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, -1);
    }

    return calendar.getTime();
  }

  /**
   * 获取日历上显示的最后一天
   *
   * @param endDate 日期
   */
  public static Date getCalendarEndDay(Date endDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    return calendar.getTime();
  }

  /**
   * 返回当前日期
   *
   * @return date
   */
  public static Date nowDate() {
    return new Date();
  }

  /**
   * 判断[指定日期时间]是否在[当前日期时间]之后
   *
   * @param date 指定日期时间
   * @return boolean
   */
  public static boolean afterNow(Date date) {
    return date.getTime() > new Date().getTime();
  }

  /**
   * 判断[指定日期时间]是否在[当前日期时间]之前
   *
   * @param date 指定日期时间
   * @return boolean
   */
  public static boolean beforeNow(Date date) {
    return date.getTime() < new Date().getTime();
  }

  /**
   * 判断指定日期是否在当前日期之前
   *
   * @param date 指定日期
   * @return boolean
   */
  public static boolean beforeToday(Date date) {
    return beforeDay(date, new Date());
  }

  /**
   * 判断指定日期是否在当前日期之后
   *
   * @param date 指定日期
   * @return boolean
   */
  public static boolean afterToday(Date date) {
    return afterDay(date, new Date());
  }

  /**
   * 比较两个日期 srcDate < destDate 则返回true，否则返回false
   *
   * @param srcDate  要比较的日期
   * @param destDate 参考日期
   * @return boolean
   */
  public static boolean beforeDay(Date srcDate, Date destDate) {
    Calendar fromCalendar = Calendar.getInstance();
    fromCalendar.setTime(srcDate);
    setBeginTime(fromCalendar);

    Calendar nowCalendar = Calendar.getInstance();
    nowCalendar.setTime(destDate);
    setBeginTime(nowCalendar);

    return fromCalendar.getTime().getTime() < nowCalendar.getTime().getTime();
  }

  /**
   * 比较两个日期 srcDate > destDate 则返回true，否则返回false
   *
   * @param srcDate  要比较的日期
   * @param destDate 参考日期
   * @return boolean
   */
  public static boolean afterDay(Date srcDate, Date destDate) {
    Calendar fromCalendar = Calendar.getInstance();
    fromCalendar.setTime(srcDate);
    setBeginTime(fromCalendar);

    Calendar nowCalendar = Calendar.getInstance();
    nowCalendar.setTime(destDate);
    setBeginTime(nowCalendar);

    return fromCalendar.getTime().getTime() > nowCalendar.getTime().getTime();
  }

  /**
   * 判断是否合法的范围
   *
   * @param begin 开始日期
   * @param end   结束日期
   * @return boolean
   */
  public static boolean isValidRange(Date begin, Date end) {
    Precondition.checkNotNull(begin, "begin date cannot be null.");
    Precondition.checkNotNull(end, "end date cannot be null.");
    return begin.getTime() <= end.getTime();
  }

  /**
   * 校验日期格式是否合法
   *
   * @param dateStr        date str
   * @param dateFormatEnum DateFormatEnum
   * @return boolean
   */
  public static boolean isValid(String dateStr, DateFormatEnum dateFormatEnum) {
    Precondition.checkNotNull(dateFormatEnum, "date format enum cannot be null");
    SimpleDateFormat sdf = getSdf(dateFormatEnum);
    // 严格模式
    sdf.setLenient(false);
    try {
      sdf.parse(dateStr);
      return true;
    } catch (ParseException e) {
      log.error("parse date exception, date str={}, format={}", dateStr, dateFormatEnum.getValue());
    }

    return false;
  }

  /**
   * 判断日期是否符合指定的格式
   *
   * @param dateList       date str list
   * @param dateFormatEnum date time format
   * @return boolean
   */
  public static boolean isValid(List<String> dateList, DateFormatEnum dateFormatEnum) {
    Precondition.checkNotNull(dateList);
    Precondition.checkNotNull(dateFormatEnum);

    SimpleDateFormat sdf = getSdf(dateFormatEnum);
    // 严格模式
    sdf.setLenient(false);

    boolean valid = true;
    for (String date : dateList) {
      try {
        sdf.parse(date);
        valid = true;
      } catch (ParseException e) {
        log.error("parse date exception, date str={}, format={}", date, dateFormatEnum.getValue());
        valid = false;
      }
      if (!valid) {
        break;
      }
    }

    return valid;
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
   * @return boolean
   */
  public static boolean isInRange(Date targetDate, Date beginDate, Date endDate) {
    return isInRange(targetDate, beginDate, endDate, RangeModeEnum.CLOSE_CLOSE);
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
   * @return boolean
   * @see RangeModeEnum
   */
  public static boolean isInRange(Date targetDate, Date beginDate, Date endDate, RangeModeEnum rangeMode) {
    Precondition.checkNotNull(targetDate, "target date cannot be null");
    Precondition.checkNotNull(beginDate, "begin date cannot be null");
    Precondition.checkNotNull(endDate, "end date cannot be null");
    Precondition.checkNotNull(endDate, "range mode cannot be null");

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
   * @return boolean
   */
  public static boolean isInRange(Date targetBeginDate, Date targetEndDate, Date totalBeginDate, Date totalEndDate) {
    Precondition.checkNotNull(targetBeginDate, "target begin date cannot be null");
    Precondition.checkNotNull(targetEndDate, "target end date cannot be null");
    Precondition.checkNotNull(totalBeginDate, "total begin date cannot be null");
    Precondition.checkNotNull(totalEndDate, "total end date cannot be null");

    boolean flag1 = targetBeginDate.getTime() >= totalBeginDate.getTime();
    boolean flag2 = targetBeginDate.getTime() <= targetEndDate.getTime();
    boolean flag3 = targetEndDate.getTime() <= totalEndDate.getTime();

    return flag1 && flag2 && flag3;
  }

  /**
   * 判断MMdd是否在指定的时间范围内
   *
   * @param targetDate 目标日期
   * @param beginDate  开始日期
   * @param endDate    结束日期
   * @param rangeMode  比较模式
   * @return boolean
   */
  public static boolean isInRangeByMonthAndDay(Date targetDate, Date beginDate, Date endDate, RangeModeEnum rangeMode) {
    Precondition.checkNotNull(targetDate, "target date cannot be null");
    Precondition.checkNotNull(beginDate, "begin date cannot be null");
    Precondition.checkNotNull(endDate, "end date cannot be null");
    Precondition.checkNotNull(rangeMode, "range mode cannot be null");

    SimpleDateFormat sdf = getSdf(DateFormatEnum.MMdd);
    int targetMonth = Integer.parseInt(sdf.format(targetDate));
    int beginMonth = Integer.parseInt(sdf.format(beginDate));
    int endMonth = Integer.parseInt(sdf.format(endDate));

    switch (rangeMode) {
      case OPEN_OPEN:
        return beginMonth < targetMonth && targetMonth < endMonth;
      case OPEN_CLOSE:
        return beginMonth < targetMonth && targetMonth <= endMonth;
      case CLOSE_OPEN:
        return beginMonth <= targetMonth && targetMonth < endMonth;
      case CLOSE_CLOSE:
        return beginMonth <= targetMonth && targetMonth <= endMonth;
    }

    return false;
  }

  /**
   * 判断是否有交集
   *
   * @param targetBeginDate 目标日期-开始
   * @param targetEndDate   目标日期-结束
   * @param totalBeginDate  总日期-开始
   * @param totalEndDate    总日期-结束
   * @return boolean
   */
  public static boolean hasIntersection(Date targetBeginDate, Date targetEndDate, Date totalBeginDate,
    Date totalEndDate) {
    Precondition.checkNotNull(targetBeginDate, "target begin date cannot be null");
    Precondition.checkNotNull(targetEndDate, "target end date cannot be null");
    Precondition.checkNotNull(totalBeginDate, "total begin date cannot be null");
    Precondition.checkNotNull(totalEndDate, "total end date cannot be null");

    boolean flag1 = targetBeginDate.getTime() >= totalEndDate.getTime();
    boolean flag2 = targetEndDate.getTime() <= totalBeginDate.getTime();

    return !(flag1 || flag2);
  }

  /**
   * 获取当前时间所在月的开始日期和结束日期
   *
   * @return date[]
   */
  public static Date[] getBeginAndEndOfMonth() {
    return getBeginAndEndOfMonth(0);
  }

  /**
   * 获取当前时间所在月的开始日期和结束日期
   *
   * @param monthOffset 偏移量  0本月，-1上月，-2上上月，1下月，2下下月；依次类推
   * @return date[]
   */
  public static Date[] getBeginAndEndOfMonth(int monthOffset) {
    return getBeginAndEndOfMonth(new Date(), monthOffset);
  }

  /**
   * 获取当前时间所在月的开始日期和结束日期
   *
   * @param date        指定日期
   * @param monthOffset 偏移量  0本月，-1上月，-2上上月，1下月，2下下月；依次类推
   * @return date[]
   */
  public static Date[] getBeginAndEndOfMonth(Date date, int monthOffset) {
    Precondition.checkNotNull(date);

    Date realDate = addMonth(date, monthOffset);
    return new Date[]{getBeginOfMonth(realDate), getEndOfMonth(realDate)};
  }

  /**
   * 月初
   *
   * @param date
   * @return
   */
  public static Date getBeginOfMonth(Date date) {
    Precondition.checkNotNull(date, "date不能为空");

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    setBeginTime(cal);

    return cal.getTime();
  }

  /**
   * 月末
   *
   * @param date
   * @return
   */
  public static Date getEndOfMonth(Date date) {
    Precondition.checkNotNull(date, "date不能为空");

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    setEndTime(cal);

    return cal.getTime();
  }


  /**
   * 获取当前时间所在周的开始日期和结束日期
   *
   * @return date[]
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
   * @param date       日期
   * @param weekOffset 0本周，-1上周，-2上上周，1下周，2下下周；依次类推
   * @return date[]
   */
  public static Date[] getBeginAndEndOfWeek(Date date, int weekOffset) {
    Precondition.checkNotNull(date);

    Date realDate = addWeek(date, weekOffset);
    return new Date[]{getBeginOfWeek(realDate), getEndOfWeek(realDate)};
  }

  /**
   * 指定日期的周一
   *
   * @param date
   * @return
   */
  public static Date getBeginOfWeek(Date date) {
    Precondition.checkNotNull(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.WEEK_OF_MONTH, 0);
    cal.set(Calendar.DAY_OF_WEEK, 2);

    setBeginTime(cal);

    return cal.getTime();
  }

  /**
   * 指定日期的周日
   *
   * @param date
   * @return
   */
  public static Date getEndOfWeek(Date date) {
    Precondition.checkNotNull(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
    cal.add(Calendar.DAY_OF_WEEK, 1);

    setEndTime(cal);

    return cal.getTime();
  }


  /**
   * 当天的开始时间 如2020-09-11 00:00:00
   *
   * @return date
   */
  public static Date getBeginDateTimeOfDay() {
    return getBeginDateTimeOfDay(new Date());
  }

  /**
   * 当天的开始时间 如2020-09-11 23:59:59.999
   *
   * @return date
   */
  public static Date getEndDateTimeOfDay() {
    return getEndDateTimeOfDay(new Date());
  }

  /**
   * 指定日期的开始和结束日期
   *
   * @param date 指定日期
   * @return date[]
   */
  public static Date[] getBeginAndEndDateTimeOfDay(final Date date) {
    return new Date[]{getBeginDateTimeOfDay(date), getEndDateTimeOfDay(date)};
  }

  /**
   * get end date time of date
   *
   * @param date date
   * @return date
   */
  public static Date getBeginDateTimeOfDay(final Date date) {
    Precondition.checkNotNull(date);

    final Calendar c = Calendar.getInstance();
    c.setLenient(false);
    c.setTime(date);
    setBeginTime(c);
    return c.getTime();
  }

  /**
   * get end date time of date
   *
   * @param date date
   * @return date
   */
  public static Date getEndDateTimeOfDay(final Date date) {
    Precondition.checkNotNull(date);

    final Calendar c = Calendar.getInstance();
    c.setLenient(false);
    c.setTime(date);
    setEndTime(c);
    return c.getTime();
  }


  /**
   * get duration time str ,eg.: 1天1小时1分钟
   *
   * @param start
   * @param end
   * @param durationTimeEnum
   * @return
   */
  public static String getDurationTimeStr(Date start, Date end, DurationTimeEnum durationTimeEnum) {
    if (start == null || end == null || durationTimeEnum == null) {
      log.warn("start or end is empty.");
      return "--";
    }
    //
    long days = 0, hours = 0, minutes = 0, seconds = 0;
    ///
    long delta = end.getTime() - start.getTime();
    if (delta <= 0) {
      log.warn("delta is zero");
    } else {
      //天数计算
      days = (delta / 1000) / (24 * 3600);
      //小时计算
      hours = (delta / 1000) % (24 * 3600) / 3600;
      //分钟计算
      minutes = (delta / 1000) % 3600 / 60;
      // 秒
      seconds = (delta / 1000) / 1000;
    }
    Map<String, String> param = new HashMap<>();
    param.put("day", "" + days);
    param.put("hour", "" + hours);
    param.put("minute", "" + minutes);
    param.put("second", "" + seconds);
    //
    String durationTimeStr = "";
    //
    if (durationTimeEnum == DurationTimeEnum.DD_HH_MM_SS_IF_NECESSARY) {
      //(${day}天)(${hour}小时)(${minute}分钟)${second}秒
      durationTimeStr = MessageUtil.replace(DurationTimeEnum.DD_HH_MM_SS.getFormat(), param);
      durationTimeStr = replaceStart(durationTimeStr, "0天");
      durationTimeStr = replaceStart(durationTimeStr, "0小时");
      durationTimeStr = replaceStart(durationTimeStr, "0分钟");
    } else if (durationTimeEnum == DurationTimeEnum.DD_HH_MM_IF_NECESSARY) {
      durationTimeStr = MessageUtil.replace(DurationTimeEnum.DD_HH_MM.getFormat(), param);
      durationTimeStr = replaceStart(durationTimeStr, "0天");
      durationTimeStr = replaceStart(durationTimeStr, "0小时");
    } else if(durationTimeEnum == DurationTimeEnum.DD_HH_IF_NECESSARY){
      durationTimeStr = MessageUtil.replace(DurationTimeEnum.DD_HH.getFormat(), param);
      durationTimeStr = replaceStart(durationTimeStr, "0天");
      durationTimeStr = replaceStart(durationTimeStr, "0小时");
    } else {
      durationTimeStr = MessageUtil.replace(durationTimeEnum.getFormat(), param);
    }

    //
    return StringUtil.defaultIfBlank(durationTimeStr, "--");
  }

  // --------------------------------private method

  private static String replaceStart(String content, String start) {
    if (content.startsWith(start)) {
      return content.replace(start, "");
    }
    return content;
  }

  private static String format(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  private static Date format(String dateStr, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);

    try {
      return sdf.parse(dateStr);
    } catch (ParseException e) {
      log.error("parse date exception", e);
      return null;
    }
  }

  /**
   * 设置指定字段值内容
   *
   * @param date
   * @param calendarField
   * @param amount
   * @return
   */
  private static Date set(final Date date, final int calendarField, final int amount) {
    Precondition.checkNotNull(date);
    // getInstance() returns a new object, so this method is thread safe.
    final Calendar c = Calendar.getInstance();
    c.setLenient(false);
    c.setTime(date);
    c.set(calendarField, amount);
    return c.getTime();
  }

  /**
   * 将日期部分设置成1970-1-1
   *
   * @param calendar
   */
  private static void setBeginDate(final Calendar calendar) {
    calendar.set(Calendar.YEAR, 1970);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
  }

  /**
   * 设置开始时间未00:00:00.000
   *
   * @param calendar
   */
  private static void setBeginTime(final Calendar calendar) {
    set(calendar, 0, 0, 0, 0);
  }

  /**
   * 设置开始时间未23:59:59.999
   *
   * @param calendar
   */
  private static void setEndTime(final Calendar calendar) {
    set(calendar, 23, 59, 59, 999);
  }

  /**
   * 设置时间部分
   *
   * @param calendar
   * @param hour
   * @param minute
   * @param second
   * @param millisecond
   */
  private static void set(final Calendar calendar, final int hour, final int minute, final int second,
    final int millisecond) {
    calendar.set(Calendar.HOUR_OF_DAY, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
  }
}
