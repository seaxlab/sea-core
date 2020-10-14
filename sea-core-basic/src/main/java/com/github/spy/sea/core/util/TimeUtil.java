package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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

        SimpleDateFormat format = new SimpleDateFormat(timeFormatStr);

        try {
            // 1970
            Date beginDate = format.parse(beginTime);
            Date endDate = format.parse(endTime);

            // now
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            calendar.setTime(new Date());

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            Date now;
            if (EqualUtil.isEq(timeFormatStr, FORMAT_HHmmSS)) {
                now = format.parse(hour + ":" + minute + ":" + second);
            } else {
                now = format.parse(hour + ":" + minute);
            }

            return now.before(endDate) && now.after(beginDate);
        } catch (Exception e) {
            log.error("fail to parse time", e);
        }

        return false;
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
