package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.DurationTimeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/8/13
 * @since 1.0
 */
@Slf4j
public class DurationUtilTest {

  @Test
  public void testDuration() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date start = sdf.parse("2020-01-01 10:00:00");
    Date end = sdf.parse("2025-08-12 20:05:30");

   log.info("Y:   " + DurationUtil.parse(start, end, DurationTimeEnum.Y));       // 5年
   log.info("YM:  " + DurationUtil.parse(start, end, DurationTimeEnum.YM));      // 5年7个月
   log.info("YMD: " + DurationUtil.parse(start, end, DurationTimeEnum.YMD));     // 5年7个月223天
   log.info("YD:  " + DurationUtil.parse(start, end, DurationTimeEnum.YD));      // 5年223天
   log.info("YDHM: " + DurationUtil.parse(start, end, DurationTimeEnum.YDHm));     // 5年223天10小时5分钟
   log.info("YDHms: " + DurationUtil.parse(start, end, DurationTimeEnum.YDHmS));     // 5年223天10小时5分钟30秒

  }

  //
  //@Test
  //public void testGetDurationTimeStr() throws Exception {
  //  Date start = DateUtil.of(2020, 1, 31, 1, 8, 30);
  //  Date end = DateUtil.of(2021, 5, 10, 12, 8, 12);
  //
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.Y,"0年"));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.YM));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.YMD));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.YMDH));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.YMDHm));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.YMDHmS));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.YD,"0天"));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.MD));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.DHW));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.WDHmS));
  //  log.info("{}", DateUtil.getDurationTimeStr(start, end, DurationTimeEnum.HMS));
  //
  //}
  //
  //@Test
  //public void testGetDurationTimeStrForYearAndDay() {
  //  Date now = new Date();
  //  String[] testCases = {
  //    DateUtil.toString(now), // 当前
  //    "2020-02-28 00:00:00", // 开始于闰年2月28日
  //    "2020-03-01 00:00:00", // 开始于闰年3月1日
  //    "2019-02-28 00:00:00", // 开始于平年2月28日
  //    "2000-01-01 00:00:00"  // 跨越2000年（闰年）
  //  };
  //
  //  for (String testCase : testCases) {
  //    log.info("从{}到现在：{}", testCase, DateUtil.getDurationTimeStr(DateUtil.toDate(testCase), //
  //      now, DurationTimeEnum.YD));
  //  }
  //}
}
