package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.enums.DurationTimeEnum;
import com.github.seaxlab.core.enums.RangeModeEnum;
import com.github.seaxlab.core.test.util.TestUtil;
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
    log.info("{}", DateUtil.toDate("2020-09-01 12:12:12"));
  }

  @Test
  public void testToString() throws Exception {
    TestUtil.println(DateUtil.toString(new Date(), "MM-dd*HH:mm"));
  }

  @Test
  public void testTradingDay() throws Exception {
    Date tradingDay = DateUtil.getTradingDay();
    log.info("{}", tradingDay);
    log.info("{}", tradingDay.getTime());
    log.info("{}", DateUtil.getMilliSecond(tradingDay)); //0
    log.info("{}", DateUtil.getMilliSecond(new Date())); // xx

    log.info("{}", DateUtil.getPreTradingDay());
    log.info("{}", DateUtil.getNextTradingDay());
  }


  @Test
  public void testAdd() throws Exception {

    Date now = DateUtil.nowDate();

    TestUtil.println(now);
    TestUtil.println(DateUtil.addDay(now, 1));
    TestUtil.println(DateUtil.addHour(now, 1));
    TestUtil.println(DateUtil.addMinute(now, 15));
    TestUtil.println(DateUtil.addSecond(now, 15));
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
    Date start = DateUtil.toDate("2021-04-01", DateFormatEnum.yyyy_MM_dd.getValue());
    Date end = DateUtil.toDate("2021-06-02", DateFormatEnum.yyyy_MM_dd.getValue());
    log.info("days={}", DateUtil.betweenDayList(start, end));
  }

  @Test
  public void testDiffSimple() throws Exception {
    Date start = DateUtil.toDate("2021-04-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date end = DateUtil.toDate("2021-04-02 13:01:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    log.info("minutes={}", DateUtil.diffSimple(start, end, ChronoUnit.MINUTES));
    log.info("days={}", DateUtil.diffSimple(start, end, ChronoUnit.DAYS));
  }

  @Test
  public void testDiff() throws Exception {
    Date start = DateUtil.toDate("2021-04-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date end = DateUtil.toDate("2021-04-02 10:01:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("minutes={}", DateUtil.diff(start, end, TimeUnit.MINUTES));
    log.info("days={}", DateUtil.diff(start, end, TimeUnit.DAYS));
  }

  @Test
  public void testParseToDate() throws Exception {
    Date start = DateUtil.toDate("2021-04-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date end = DateUtil.toDate("2021-04-02 10:01:01", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    log.info("{}",
      DateUtil.toString(DateUtil.toDateByDateAndTime(start, end), DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue()));
  }

  @Test
  public void testIsMonthAndDayInRange() throws Exception {
    Date start = DateUtil.toDate("2021-05-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date end = DateUtil.toDate("2021-12-10 10:01:01", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    Date target = DateUtil.toDate("2021-04-03 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date target2 = DateUtil.toDate("2021-08-03 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date target3 = DateUtil.toDate("2021-12-13 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    log.info("{}", DateUtil.isInRangeByMonthAndDay(target, start, end, RangeModeEnum.CLOSE_CLOSE));
    log.info("{}", DateUtil.isInRangeByMonthAndDay(target2, start, end, RangeModeEnum.CLOSE_CLOSE));
    log.info("{}", DateUtil.isInRangeByMonthAndDay(target3, start, end, RangeModeEnum.CLOSE_CLOSE));

  }

  @Test
  public void testHasIntersection2() throws Exception {
    Date totalStart = DateUtil.toDate("2021-04-01 08:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date totalEnd = DateUtil.toDate("2021-04-01 09:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    Date start, end;
    start = DateUtil.toDate("2021-04-01 08:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    end = DateUtil.toDate("2021-04-01 08:30:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

  }

  @Test
  public void testHasIntersection3() throws Exception {
    Date totalStart = DateUtil.toDate("2021-04-01 08:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date totalEnd = DateUtil.toDate("2021-04-01 09:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    Date start, end;
    start = DateUtil.toDate("2021-04-01 07:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    end = DateUtil.toDate("2021-04-01 08:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

  }


  @Test
  public void testHasIntersection() throws Exception {

    Date totalStart = DateUtil.toDate("2021-04-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    Date totalEnd = DateUtil.toDate("2021-04-01 13:01:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    Date start, end;
    start = DateUtil.toDate("2021-04-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    end = DateUtil.toDate("2021-04-01 14:01:01", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

    start = DateUtil.toDate("2021-04-01 13:01:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    end = DateUtil.toDate("2021-04-01 14:01:01", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

    start = DateUtil.toDate("2021-04-01 08:01:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    end = DateUtil.toDate("2021-04-01 12:00:01", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));

    start = DateUtil.toDate("2021-04-01 08:01:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    end = DateUtil.toDate("2021-04-01 12:00:00", DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());
    log.info("intersection={}", DateUtil.hasIntersection(start, end, totalStart, totalEnd));
  }

  @Test
  public void testSet() throws Exception {
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
  public void testIsValid() throws Exception {

    Date begin = DateUtil.toDate("2021-04-01 13:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
    Date end = DateUtil.toDate("2021-04-01 13:10:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
    log.info("{}", DateUtil.isValidRange(begin, end));

    begin = DateUtil.toDate("2021-05-01 13:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
    end = DateUtil.toDate("2021-04-01 13:10:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
    log.info("{}", DateUtil.isValidRange(begin, end));

    List<String> dates = ImmutableList.of("202108-1", "2021-09-01");
    log.info("{}", DateUtil.isValid(dates, DateFormatEnum.yyyy_MM_dd));

    List<String> dates2 = ImmutableList.of("2021-19-19");
    log.info("{}", DateUtil.isValid(dates2, DateFormatEnum.yyyy_MM_dd));

    List<String> dates3 = ImmutableList.of("2021-09-01", "2021-10-11");

    log.info("{}", DateUtil.isValid(dates3, DateFormatEnum.yyyy_MM_dd));
    log.info("{}", DateUtil.isValid(dates3, DateFormatEnum.yyyy_MM_dd_HH_mm));

  }

  @Test
  public void testCompare() throws Exception {
    Date date1 = DateUtil.toDate("2021-04-01 13:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
    Date date2 = DateUtil.toDate("2021-04-01 12:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);

    log.info("compare value={}", DateUtil.compareDatePart(date1, date2));
    log.info("compare value={}", DateUtil.compareTimePart(date1, date2));
  }


  @Test
  public void testGet() throws Exception {
    Date date = DateUtil.toDate("2021-04-01 13:01:50", DateFormatEnum.yyyy_MM_dd_HH_mm_ss);

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


  @Test
  public void testGetDurationTimeStr() throws Exception {
    Date start = DateUtil.of(2021, 1, 31, 1, 8, 30);
    Date end = DateUtil.of(2021, 1, 31, 12, 8, 12);

    log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DD_HH_MM_SS));
    log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DD_HH_MM));
    log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DD_HH));
    log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DD_HH_MM_SS_IF_NECESSARY));
    log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DD_HH_MM_IF_NECESSARY));
    log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DD_HH_IF_NECESSARY));
  }

}
