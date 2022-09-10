package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.enums.RangeModeEnum;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-20
 * @since 1.0
 */
@Slf4j
public class DateUtilTest extends BaseCoreTest {

    @Test
    public void testToDate() throws Exception {
        log.info("{}", DateUtil.toDate(Instant.now()));
    }

    @Test
    public void testToString() throws Exception {
        println(DateUtil.toString(new Date(), "MM-dd*HH:mm"));
    }

    @Test
    public void testAdd() throws Exception {
        println(DateUtil.nowDate());

        Date now = DateUtil.nowDate();

        Date day1 = DateUtil.addDay(now, 1);

        println(DateUtil.addHour(now, 1));
        println(DateUtil.addMinute(now, 15));
    }

    @Test
    public void testAddDay() throws Exception {
        Date now = DateUtil.nowDate();

        Date day = DateUtil.addDay(now, 0);
        log.info("day={}", day);
    }


    @Test
    public void testToLocalDate() throws Exception {
        Date input = new Date();
        Instant instant = input.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        log.info("local date={}", date);
    }

    @Test
    public void run34() throws Exception {
        log.info("{}-{}", DateUtil.getBeginAndEndOfWeek(0));
        log.info("{}-{}", DateUtil.getBeginAndEndOfWeek(1));
    }

    @Test
    public void run40() throws Exception {
        log.info("{}-{}", DateUtil.getBeginAndEndDateTimeOfDay(new Date()));
        log.info("{}", DateUtil.getBeginDateTimeOfDay());
    }

    @Test
    public void testTruncate() throws Exception {
        for (int i = 0; i < 10000; i++) {
            log.info("{}", DateUtil.truncate(new Date(), Calendar.MINUTE));
        }
    }


    @Test
    public void testBetweenDays() throws Exception {
        Date start = DateUtil.toDate("2021-04-01", DateUtil.DAY_FORMAT);
        Date end = DateUtil.toDate("2021-06-02", DateUtil.DAY_FORMAT);
        log.info("days={}", DateUtil.betweenDayList(start, end));
    }

    @Test
    public void testDiffSimple() throws Exception {
        Date start = DateUtil.toDate("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.toDate("2021-04-02 13:01:00", DateUtil.DEFAULT_FORMAT);

        log.info("minutes={}", DateUtil.diffSimple(start, end, ChronoUnit.MINUTES));
        log.info("days={}", DateUtil.diffSimple(start, end, ChronoUnit.DAYS));
    }

    @Test
    public void testDiff() throws Exception {
        Date start = DateUtil.toDate("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.toDate("2021-04-02 10:01:00", DateUtil.DEFAULT_FORMAT);
        log.info("minutes={}", DateUtil.diff(start, end, TimeUnit.MINUTES));
        log.info("days={}", DateUtil.diff(start, end, TimeUnit.DAYS));
    }

    @Test
    public void testParseToDate() throws Exception {
        Date start = DateUtil.toDate("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.toDate("2021-04-02 10:01:01", DateUtil.DEFAULT_FORMAT);

        log.info("{}", DateUtil.toString(DateUtil.parseToDate(start, end), DateUtil.DEFAULT_FORMAT));
    }

    @Test
    public void testIsMonthAndDayInRange() throws Exception {
        Date start = DateUtil.toDate("2021-05-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.toDate("2021-12-10 10:01:01", DateUtil.DEFAULT_FORMAT);

        Date target = DateUtil.toDate("2021-04-03 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date target2 = DateUtil.toDate("2021-08-03 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date target3 = DateUtil.toDate("2021-12-13 12:00:00", DateUtil.DEFAULT_FORMAT);

        log.info("{}", DateUtil.isMonthAndDayInRange(target, start, end, RangeModeEnum.CLOSE_CLOSE));
        log.info("{}", DateUtil.isMonthAndDayInRange(target2, start, end, RangeModeEnum.CLOSE_CLOSE));
        log.info("{}", DateUtil.isMonthAndDayInRange(target3, start, end, RangeModeEnum.CLOSE_CLOSE));

    }

    @Test
    public void testHasIntersection2() throws Exception {
        Date totalStart = DateUtil.toDate("2021-04-01 08:00:00", DateUtil.DEFAULT_FORMAT);
        Date totalEnd = DateUtil.toDate("2021-04-01 09:00:00", DateUtil.DEFAULT_FORMAT);

        Date start, end;
        start = DateUtil.toDate("2021-04-01 08:00:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.toDate("2021-04-01 08:30:00", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

    }

    @Test
    public void testHasIntersection3() throws Exception {
        Date totalStart = DateUtil.toDate("2021-04-01 08:00:00", DateUtil.DEFAULT_FORMAT);
        Date totalEnd = DateUtil.toDate("2021-04-01 09:00:00", DateUtil.DEFAULT_FORMAT);

        Date start, end;
        start = DateUtil.toDate("2021-04-01 07:00:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.toDate("2021-04-01 08:00:00", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

    }


    @Test
    public void testHasIntersection() throws Exception {

        Date totalStart = DateUtil.toDate("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date totalEnd = DateUtil.toDate("2021-04-01 13:01:00", DateUtil.DEFAULT_FORMAT);

        Date start, end;
        start = DateUtil.toDate("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.toDate("2021-04-01 14:01:01", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));


        start = DateUtil.toDate("2021-04-01 13:01:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.toDate("2021-04-01 14:01:01", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));


        start = DateUtil.toDate("2021-04-01 08:01:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.toDate("2021-04-01 12:00:01", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

        start = DateUtil.toDate("2021-04-01 08:01:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.toDate("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));
    }

    @Test
    public void testSetSecond() throws Exception {
        Date now = new Date();
        Date date = DateUtil.setYear(now, 1999);
        log.info("time={}", DateUtil.toString(date, DateFormatEnum.DEFAULT));


        date = DateUtil.setMonth(now, 12);
        log.info("time={}", DateUtil.toString(date, DateFormatEnum.DEFAULT));


        date = DateUtil.setDay(now, 20);
        log.info("time={}", DateUtil.toString(date, DateFormatEnum.DEFAULT));


        date = DateUtil.setHour(now, 23);
        log.info("time={}", DateUtil.toString(date, DateFormatEnum.DEFAULT));

        date = DateUtil.setMinute(now, 10);
        log.info("time={}", DateUtil.toString(date, DateFormatEnum.DEFAULT));

        date = DateUtil.setSecond(now, 20);
        log.info("time={}", DateUtil.toString(date, DateFormatEnum.DEFAULT));
    }

    @Test
    public void testIsValidDate() throws Exception {
        List<String> dates = ImmutableList.of("202108-1", "2021-09-01");
        log.info("{}", DateUtil.isValidDate(dates, DateUtil.DAY_FORMAT));

        List<String> dates2 = ImmutableList.of("2021-19-19");
        log.info("{}", DateUtil.isValidDate(dates2, DateUtil.DAY_FORMAT));

        List<String> dates3 = ImmutableList.of("2021-09-01", "2021-10-11");

        log.info("{}", DateUtil.isValidDate(dates3, DateUtil.DAY_FORMAT));
        log.info("{}", DateUtil.isValidDate(dates3, DateUtil.DATETIME_YMHM));

    }

    @Test
    public void testGet() throws Exception {
        Date date = DateUtil.toDate("2021-04-01 13:01:50", DateUtil.DEFAULT_FORMAT);

        Assert.assertEquals(2021, DateUtil.getYear(date));
        Assert.assertEquals(4, DateUtil.getMonth(date));
        Assert.assertEquals(1, DateUtil.getDay(date));
        Assert.assertEquals(13, DateUtil.getHour(date));
        Assert.assertEquals(1, DateUtil.getMinute(date));
        Assert.assertEquals(50, DateUtil.getSecond(date));

    }

    @Test
    public void testOf1() throws Exception {
        Date date = DateUtil.of(2021, 1, 31);
        log.info("date={}", date);
    }

    @Test
    public void testOf2() throws Exception {
        Date date = DateUtil.of(2021, 1, 31, 12, 10, 30);
        log.info("date={}", date);
    }

    @Test
    public void testGetBeginAndEndOfWeek() throws Exception {

        log.info("{}", DateUtil.getBeginOfWeek(new Date()));
        log.info("{}", DateUtil.getEndOfWeek(new Date()));

        Date[] dates;
        dates = DateUtil.getBeginAndEndOfWeek(new Date(), -1);
        log.info("{},{}", dates[0], dates[1]);

        dates = DateUtil.getBeginAndEndOfWeek(new Date(), 0);
        log.info("{},{}", dates[0], dates[1]);

        dates = DateUtil.getBeginAndEndOfWeek(new Date(), 1);
        log.info("{},{}", dates[0], dates[1]);
    }

    @Test
    public void testGetBeginAndEndOfMonth() throws Exception {

        Date[] dates;

        dates = DateUtil.getBeginAndEndOfMonth(new Date(), -1);
        log.info("{},{}", dates[0], dates[1]);

        dates = DateUtil.getBeginAndEndOfMonth(new Date(), 0);
        log.info("{},{}", dates[0], dates[1]);

        dates = DateUtil.getBeginAndEndOfMonth(new Date(), 1);
        log.info("{},{}", dates[0], dates[1]);

    }

    @Test
    public void testGetBeginAndEndOfMonth2() throws Exception {
        log.info("begin={}", DateUtil.getBeginOfMonth(new Date()));
        log.info("end={}", DateUtil.getEndOfMonth(new Date()));
    }

    @Test
    public void testBetweenHours() throws Exception {
        Date start = DateUtil.of(2021, 1, 31, 1, 10, 30);
        Date end = DateUtil.of(2021, 1, 31, 12, 10, 30);

        log.info("hours list={}", DateUtil.betweenHours(start, end));
    }

    @Test
    public void testLastDay() throws Exception {
        String year = new SimpleDateFormat("yy", Locale.CHINESE).format(new Date());
        log.info("{}", year);
        year = new SimpleDateFormat("yy").format(new Date());
        log.info("{}", year);
    }

    @Test
    public void testBeginAndEndDateTime() throws Exception {
        Date[] dates = DateUtil.getBeginAndEndDateTimeOfDay(new Date());
        log.info("begin={},end={}", dates[0], dates[1]);
    }

}
