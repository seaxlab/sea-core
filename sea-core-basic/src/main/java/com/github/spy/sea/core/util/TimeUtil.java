package com.github.spy.sea.core.util;

import com.github.spy.sea.core.enums.RangeModeEnum;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.model.Tuple2;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * 时间工具类
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public final class TimeUtil {

    public static final String FORMAT_HHmmSS = "HH:mm:ss";
    public static final String FORMAT_HHmm = "HH:mm";

    private TimeUtil() {
    }

    /**
     * create time, and convert to date.
     *
     * @param hour   时
     * @param minute 分
     * @return Date
     */
    public static Date of(int hour, int minute) {
        return of(hour, minute, 0);
    }

    /**
     * create time, and convert to date
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return Date
     */
    public static Date of(int hour, int minute, int second) {
        ZoneId zone = ZoneId.systemDefault();
        return of(hour, minute, second, zone);
    }

    /**
     * create time, and convert to date
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @param zoneId 时区
     * @return Date
     */
    public static Date of(int hour, int minute, int second, ZoneId zoneId) {
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(hour, minute, second);

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        Instant instant = localDateTime.atZone(zoneId).toInstant();

        return Date.from(instant);
    }


    /**
     * format time str to date
     *
     * @param timeStr
     * @return
     */
    public static Date format(String timeStr) {
        return format(timeStr, FORMAT_HHmm);
    }

    /**
     * format time str to date.
     *
     * @param timeStr time str
     * @param format  format
     * @return
     */
    public static Date format(String timeStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(timeStr);
        } catch (Exception e) {
            log.error("fail to parse exception", e);
            ExceptionHandler.publishMsg("parse date time error");
            return null;
        }
    }

    /**
     * 判断当前时间是否在指定范围内，注意指定时间格式HH:mm
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean nowIsInRange(String beginTime, String endTime) {
        return nowIsInRange(beginTime, endTime, FORMAT_HHmm);
    }

    /**
     * 判断当前时间是否在指定范围内，注意指定时间格式
     *
     * @param beginTime
     * @param endTime
     * @param timeFormatStr
     * @return
     */
    public static boolean nowIsInRange(String beginTime, String endTime, String timeFormatStr) {
        if (StringUtil.isEmpty(timeFormatStr)) {
            timeFormatStr = FORMAT_HHmm;
        }
        try {
            // now
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            calendar.setTime(new Date());

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            String now;
            if (EqualUtil.isEq(timeFormatStr, FORMAT_HHmmSS)) {
                now = hour + ":" + minute + ":" + second;
            } else {
                now = hour + ":" + minute;
            }
            return isInRange(now, beginTime, endTime, timeFormatStr, RangeModeEnum.CLOSE_CLOSE);
        } catch (Exception e) {
            log.error("fail to parse time", e);
        }

        return false;
    }

    /**
     * 判断是否在时间范围内
     *
     * @param targetTime
     * @param timeList
     * @param timeFormatStr
     * @param rangeMode
     * @return
     */
    public static boolean isInRange(String targetTime, List<Tuple2<String, String>> timeList,
                                    String timeFormatStr, RangeModeEnum rangeMode) {

        Preconditions.checkNotNull(targetTime, "target time cannot be null");
        if (timeList == null || timeList.isEmpty()) {
            return false;
        }

        for (int i = 0; i < timeList.size(); i++) {
            Tuple2<String, String> tuple = timeList.get(i);
            boolean ret = isInRange(targetTime, tuple.getFirst(), tuple.getSecond(), timeFormatStr, rangeMode);
            if (ret) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断时间是否在指定范围内
     *
     * @param targetTime    目标时间
     * @param beginTime     开始时间
     * @param endTime       结束时间
     * @param timeFormatStr 时间格式
     * @param rangeMode     区间范围
     * @return
     */
    public static boolean isInRange(String targetTime, String beginTime, String endTime,
                                    String timeFormatStr, RangeModeEnum rangeMode) {
        Preconditions.checkNotNull(targetTime, "target time cannot be null");
        Preconditions.checkNotNull(beginTime, "begin time cannot be null");
        Preconditions.checkNotNull(endTime, "end time cannot be null");
        Preconditions.checkNotNull(rangeMode, "区间模式不能为空");

//        if (targetTime.length() != beginTime.length() || beginTime.length() != endTime.length()) {
//            log.warn("targetTime,beginTime,endTime不相同:[{},{},{}]", targetTime.length(), beginTime.length(), endTime.length());
//            return false;
//        }

        if (StringUtil.isEmpty(timeFormatStr)) {
            timeFormatStr = FORMAT_HHmm;
        }

        SimpleDateFormat format = new SimpleDateFormat(timeFormatStr);

        try {
            // 1970
            Date now = format.parse(targetTime);
            Date beginDate = format.parse(beginTime);
            Date endDate = format.parse(endTime);

            //这个没有区间包含
//            now.before(endDate) && now.after(beginDate);

            switch (rangeMode) {
                case OPEN_OPEN:
                    return beginDate.getTime() < now.getTime() && now.getTime() < endDate.getTime();

                case OPEN_CLOSE:
                    return beginDate.getTime() < now.getTime() && now.getTime() <= endDate.getTime();

                case CLOSE_OPEN:
                    return beginDate.getTime() <= now.getTime() && now.getTime() < endDate.getTime();

                case CLOSE_CLOSE:
                    return beginDate.getTime() <= now.getTime() && now.getTime() <= endDate.getTime();
            }

        } catch (Exception e) {
            log.error("fail to parse time", e);
        }

        return false;
    }

    /**
     * 判断当前时间是否在指定时间范围内
     *
     * @param timeList
     * @param timeFormatStr
     * @return
     */
    public static boolean nowIsInRange(List<Tuple2<String, String>> timeList, String timeFormatStr) {
        if (ListUtil.isEmpty(timeList)) {
            log.warn("timeList is empty.");
            return false;
        }

        for (int i = 0; i < timeList.size(); i++) {
            Tuple2<String, String> tuple = timeList.get(i);
            boolean isIn = nowIsInRange(tuple.getFirst(), tuple.getSecond(), timeFormatStr);
            if (isIn) {
                return true;
            }
        }

        return false;
    }

    /**
     * compare two time part only.
     *
     * @param date1 datetime
     * @param date2 datetime
     * @return
     */
    public static int compare(Date date1, Date date2) {
        DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();
        return comparator.compare(date1, date2);
    }

    /**
     * 比较时间,默认格式HH:mm
     * 如果time1<time2,返回-1
     * 如果time1=time2,返回0
     * 如果time1>time2,返回1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compare(String time1, String time2) {
        return compare(time1, time2, FORMAT_HHmm);
    }

    /**
     * 比较时间
     * 如果time1<time2,返回-1
     * 如果time1=time2,返回0
     * 如果time1>time2,返回1
     *
     * @param time1         time2
     * @param time2         time2
     * @param timeFormatStr time format
     * @return
     */
    public static int compare(String time1, String time2, String timeFormatStr) {
        Preconditions.checkNotNull(time1);
        Preconditions.checkNotNull(time2);
        Preconditions.checkNotNull(timeFormatStr);
        SimpleDateFormat format = new SimpleDateFormat(timeFormatStr);

        try {
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);

            return date1.compareTo(date2);
        } catch (ParseException e) {
            log.error("date parse exception", e);
        }

        throw new IllegalStateException();
    }

    /**
     * 增加时间
     *
     * @param timeStr
     * @param timeFormatStr
     * @param delta
     * @param timeUnit
     * @return
     */
    public static String add(String timeStr, String timeFormatStr, int delta, TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeStr);
        Preconditions.checkNotNull(timeFormatStr);
        Preconditions.checkNotNull(timeUnit);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormatStr);
        LocalTime time = LocalTime.parse(timeStr, timeFormatter);

        LocalTime newTime = add(time, delta, timeUnit);
        return newTime.format(timeFormatter);
    }

    /**
     * 增加时间
     *
     * @param time
     * @param delta
     * @param timeUnit
     * @return
     */
    public static LocalTime add(LocalTime time, int delta, TimeUnit timeUnit) {
        Preconditions.checkNotNull(time);
        Preconditions.checkNotNull(timeUnit);

        LocalTime newTime;
        switch (timeUnit) {
            case HOURS:
                newTime = time.plusHours(delta);
                break;
            case MINUTES:
                newTime = time.plusMinutes(delta);
                break;
            case SECONDS:
                newTime = time.plusSeconds(delta);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return newTime;
    }


    /**
     * to string with time unit. eg: 100 ns; 120ms
     *
     * @param nanos
     * @return
     */
    public static String toTimeUnit(long nanos) {
        TimeUnit unit = chooseUnit(nanos);
        double value = (double) nanos / NANOSECONDS.convert(1, unit);

        return String.format(Locale.ROOT, "%.4g", value) + " " + abbreviate(unit);
    }

    private static TimeUnit chooseUnit(long nanos) {
        if (DAYS.convert(nanos, NANOSECONDS) > 0) {
            return DAYS;
        }
        if (HOURS.convert(nanos, NANOSECONDS) > 0) {
            return HOURS;
        }
        if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
            return MINUTES;
        }
        if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
            return SECONDS;
        }
        if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
            return MILLISECONDS;
        }
        if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
            return MICROSECONDS;
        }
        return NANOSECONDS;
    }

    private static String abbreviate(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "ns";
            case MICROSECONDS:
                return "\u03bcs"; // μs
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "s";
            case MINUTES:
                return "min";
            case HOURS:
                return "h";
            case DAYS:
                return "d";
            default:
                throw new AssertionError();
        }
    }


}
