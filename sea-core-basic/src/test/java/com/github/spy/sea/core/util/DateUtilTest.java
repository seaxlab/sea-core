package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.enums.RangeModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
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
    public void run17() throws Exception {
        println(DateUtil.nowStr());
        println(DateUtil.nowDate());
        println(DateUtil.now("MM-dd*HH:mm"));

        Date now = DateUtil.nowDate();

        Date day1 = DateUtil.addDay(now, 1);

        println(DateUtil.addHour(now, 1));
        println(DateUtil.addMinute(now, 15));
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
    public void parseTest() throws Exception {
        log.info("{}", DateUtil.parse(Instant.now()));
    }

    @Test
    public void testTruncate() throws Exception {
        for (int i = 0; i < 10000; i++) {
            log.info("{}", DateUtil.truncate(new Date(), Calendar.MINUTE));
        }
    }


    @Test
    public void testBetweenDays() throws Exception {
        Date start = DateUtil.str2Date("2021-04-01", DateUtil.DAY_FORMAT);
        Date end = DateUtil.str2Date("2021-06-02", DateUtil.DAY_FORMAT);
        log.info("days={}", DateUtil.betweenDayList(start, end));
    }

    @Test
    public void testDiffSimple() throws Exception {
        Date start = DateUtil.str2Date("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.str2Date("2021-04-02 13:01:00", DateUtil.DEFAULT_FORMAT);

        log.info("minutes={}", DateUtil.diffSimple(start, end, ChronoUnit.MINUTES));
        log.info("days={}", DateUtil.diffSimple(start, end, ChronoUnit.DAYS));
    }

    @Test
    public void testDiff() throws Exception {
        Date start = DateUtil.str2Date("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.str2Date("2021-04-02 10:01:00", DateUtil.DEFAULT_FORMAT);
        log.info("minutes={}", DateUtil.diff(start, end, TimeUnit.MINUTES));
        log.info("days={}", DateUtil.diff(start, end, TimeUnit.DAYS));
    }

    @Test
    public void testParseToDate() throws Exception {
        Date start = DateUtil.str2Date("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.str2Date("2021-04-02 10:01:01", DateUtil.DEFAULT_FORMAT);

        log.info("{}", DateUtil.toString(DateUtil.parseToDate(start, end), DateUtil.DEFAULT_FORMAT));
    }

    @Test
    public void testIsMonthAndDayInRange() throws Exception {
        Date start = DateUtil.str2Date("2021-05-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date end = DateUtil.str2Date("2021-12-10 10:01:01", DateUtil.DEFAULT_FORMAT);

        Date target = DateUtil.str2Date("2021-04-03 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date target2 = DateUtil.str2Date("2021-08-03 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date target3 = DateUtil.str2Date("2021-12-13 12:00:00", DateUtil.DEFAULT_FORMAT);

        log.info("{}", DateUtil.isMonthAndDayInRange(target, start, end, RangeModeEnum.CLOSE_CLOSE));
        log.info("{}", DateUtil.isMonthAndDayInRange(target2, start, end, RangeModeEnum.CLOSE_CLOSE));
        log.info("{}", DateUtil.isMonthAndDayInRange(target3, start, end, RangeModeEnum.CLOSE_CLOSE));

    }

    @Test
    public void testHasIntersection() throws Exception {

        Date totalStart = DateUtil.str2Date("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        Date totalEnd = DateUtil.str2Date("2021-04-01 13:01:00", DateUtil.DEFAULT_FORMAT);

        Date start, end;
        start = DateUtil.str2Date("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.str2Date("2021-04-01 14:01:01", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));


        start = DateUtil.str2Date("2021-04-01 13:01:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.str2Date("2021-04-01 14:01:01", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));


        start = DateUtil.str2Date("2021-04-01 08:01:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.str2Date("2021-04-01 12:00:01", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

        start = DateUtil.str2Date("2021-04-01 08:01:00", DateUtil.DEFAULT_FORMAT);
        end = DateUtil.str2Date("2021-04-01 12:00:00", DateUtil.DEFAULT_FORMAT);
        log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));
    }

    @Test
    public void testSetSecond() throws Exception {
        Date now = new Date();
        Date date = DateUtil.setYear(now, 1999);
        log.info("time={}", DateUtil.toYMDHMS(date));


        date = DateUtil.setMonth(now, 12);
        log.info("time={}", DateUtil.toYMDHMS(date));


        date = DateUtil.setDay(now, 20);
        log.info("time={}", DateUtil.toYMDHMS(date));


        date = DateUtil.setHour(now, 8);
        log.info("time={}", DateUtil.toYMDHMS(date));

        date = DateUtil.setMinute(now, 10);
        log.info("time={}", DateUtil.toYMDHMS(date));

        date = DateUtil.setSecond(now, 20);
        log.info("time={}", DateUtil.toYMDHMS(date));


    }
}
