package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.DurationTimeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间差格式化工具类
 * <pre>
 * 本类提供多种时间差格式化方法，如：年、年月、年月日、年天、月天、时分秒、周天时分秒等。
 * 关于闰年（Leap Year）的说明：
 * - Java 8 的 java.time 包（如 LocalDate、Period、ChronoUnit）会自动处理闰年。
 * - 例如：2020、2024 是闰年（366天），2100 不是闰年（虽然是整百年但不能被400整除）。
 * - 所有基于 Period 或 ChronoUnit 的计算都会考虑实际日历规则，包括闰年2月29日。
 * - 因此，跨闰年的日期差计算是准确的，无需手动干预。
 * </pre>
 *
 * @author spy
 * @version 1.0 2025/8/13
 * @since 1.0
 */
@Slf4j
public final class DurationUtil {

  /**
   * 转换成可读时间
   *
   * @param start            开始时间
   * @param end              结束时间
   * @param durationTimeEnum 格式
   * @return a年b天c小时d分钟e秒等格式
   */
  public static String parse(Date start, Date end, DurationTimeEnum durationTimeEnum) {
    //默认值,年天小时分钟秒
    durationTimeEnum = durationTimeEnum == null ? DurationTimeEnum.YDHmS : durationTimeEnum;
    //
    switch (durationTimeEnum) {
      case Y: //年
        return y(start, end);
      case YM: //年月
        return ym(start, end);
      case YMD: //年月天
        return ymd(start, end);
      case YD: //年天
        return yd(start, end);
      case YDHm: //年天小时分钟
        return ydhm(start, end);
      case YDHmS: //年月天小时分钟秒
        return ydhms(start, end);
      default:
        throw new IllegalArgumentException("unsupported duration time " + durationTimeEnum.getFormat());
    }
  }


  //TODO
  //支持默认值的函数需要时再添加

  /**
   * Y 格式：只返回完整年数（忽略月、日）
   * <p>
   * ✅ 闰年处理：
   * Period.between() 会自动计算两个日期之间的完整年数。
   * 例如：从 2020-02-29（闰年）到 2021-02-28，不满一年，结果为 0 年。
   * 从 2020-02-29 到 2021-03-01，满一年，结果为 1 年。
   * 因此，该方法能正确处理闰年边界。
   */
  private static String y(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      //这个比较特殊
      return "0年";
    }
    Period period = Period.between(s.toLocalDate(), e.toLocalDate());
    return period.getYears() + "年";
  }

  /**
   * YM 格式：年 + 月（忽略日）
   * <p>
   * ✅ 闰年处理：
   * 虽然“月”不受闰年直接影响，但“年”的部分会因闰年而变化。
   * 例如：从 2020-01-01 到 2024-08-01，Period 计算为 4年7个月。
   * 因为 2020、2024 是闰年，但 Period 的年/月计算基于日历月，自动适应。
   * 无需特殊处理。
   */
  private static String ym(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      //这个比较特殊
      return "0年";
    }
    Period period = Period.between(s.toLocalDate(), e.toLocalDate());
    int years = period.getYears();
    int months = period.getMonths();
    return years + "年" + (months > 0 ? months + "个月" : "");
  }

  /**
   * YMD 格式：年 + 月 + 天（精确到日）
   * <p>
   * ✅ 闰年处理：
   * Period.between() 会精确计算年、月、日的差异，自动考虑闰年。
   * 例如：从 2020-02-28 到 2020-03-01，相差 2 天（因为 2020 是闰年，2月有29日）。
   * 从 2021-02-28 到 2021-03-01，相差 1 天（非闰年）。
   * 因此，该方法能正确反映闰年对“天数”部分的影响。
   */
  private static String ymd(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }
    Period period = Period.between(s.toLocalDate(), e.toLocalDate());
    StringBuilder sb = new StringBuilder();
    if (period.getYears() > 0) {
      sb.append(period.getYears()).append("年");
    }
    if (period.getMonths() > 0) {
      sb.append(period.getMonths()).append("个月");
    }
    if (period.getDays() > 0) {
      sb.append(period.getDays()).append("天");
    }
    return sb.toString();
  }

  /**
   * YD 格式：年 + 天（跳过月，按每年天数累加）
   * <p>
   * ⚠️ 闰年处理（关键）：
   * 此方法手动计算“年”和“剩余天数”，必须考虑闰年。
   * 使用 current.lengthOfYear() 获取当前年份的总天数（365 或 366）。
   * 例如：从 2020-01-01 到 2021-01-01，totalDays = 366（因为 2020 是闰年）。
   * 因此，years = 1，剩余天数 = 0。
   * 此实现能正确处理跨多个闰年的情况。
   */
  private static String yd(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }

    long totalDays = ChronoUnit.DAYS.between(s.toLocalDate(), e.toLocalDate());
    int years = 0;
    LocalDate current = s.toLocalDate();

    // 每次检查当前年份是否为闰年（通过 lengthOfYear() 自动判断）
    while (totalDays >= current.lengthOfYear()) {
      years++;
      totalDays -= current.lengthOfYear(); // 自动返回 365 或 366
      current = current.plusYears(1);
    }

    StringBuilder sb = new StringBuilder();
    if (years > 0) {
      sb.append(years).append("年");
    }
    if (totalDays > 0) {
      sb.append(totalDays).append("天");
    }
    return sb.toString();
  }

  /**
   * YDHM 格式：年 + 天 + 小时 + 分钟（跳过“月”）
   * <p>
   * ✅ 闰年处理：
   * - 使用 current.lengthOfYear() 获取当前年份天数（365 或 366），自动支持闰年。
   * - 例如：从 2020-01-01 到 2021-01-01，totalDays = 366（闰年），years = 1。
   * - 剩余时间部分由 ChronoUnit 精确计算，包含秒级精度。
   *
   * @param start 起始时间
   * @param end   结束时间（null 表示当前时间）
   * @return 格式化字符串，如 "5年223天10小时5分钟"
   */
  private static String ydhm(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);

    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }

    // 计算总天数
    long totalDays = ChronoUnit.DAYS.between(s.toLocalDate(), e.toLocalDate());
    int years = 0;
    LocalDate current = s.toLocalDate();

    while (totalDays >= current.lengthOfYear()) {
      years++;
      totalDays -= current.lengthOfYear();
      current = current.plusYears(1);
    }
    long days = totalDays; // 剩余天数

    // 计算时间部分的总分钟
    LocalDateTime base = s.plusYears(years).plusDays(days);
    long diffMinutes = ChronoUnit.MINUTES.between(base, e);

    long hours = diffMinutes / 60;
    long minutes = diffMinutes % 60;

    // 如果小时 >= 24，进位到天
    if (hours >= 24) {
      days += hours / 24;
      hours = hours % 24;
    }

    StringBuilder sb = new StringBuilder();
    if (years > 0) {
      sb.append(years).append("年");
    }
    if (days > 0) {
      sb.append(days).append("天");
    }
    if (hours > 0) {
      sb.append(hours).append("小时");
    }
    if (minutes > 0 || sb.length() > 0) {
      sb.append(minutes).append("分钟");
    }

    return sb.toString();
  }

  /**
   * YDHMS 格式：年 + 天 + 小时 + 分钟 + 秒（跳过“月”）
   * <p>
   * 精确计算两个时间之间的：
   * - 完整年数（自动识别闰年）
   * - 剩余天数
   * - 剩余时间（小时、分钟、秒）
   * <p>
   * 示例：2020-01-01 10:00:00 到 2025-08-12 20:05:30
   * 输出：5年223天10小时5分钟30秒
   *
   * @param start 起始时间
   * @param end   结束时间（null 表示当前时间）
   * @return 格式化字符串
   */
  private static String ydhms(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);

    // 确保 s <= e
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }

    // 1. 计算“年”和“剩余天数”
    long totalDays = ChronoUnit.DAYS.between(s.toLocalDate(), e.toLocalDate());
    int years = 0;
    LocalDate current = s.toLocalDate();

    // 扣除整年（自动处理闰年）
    while (totalDays >= current.lengthOfYear()) {
      years++;
      totalDays -= current.lengthOfYear();
      current = current.plusYears(1);
    }
    long days = totalDays; // 剩余天数

    // 2. 构建基准时间：起始时间 + years年 + days天
    LocalDateTime base = s.plusYears(years).plusDays(days);

    // 3. 计算 base 到 e 的时间差（精确到秒）
    long diffSeconds = ChronoUnit.SECONDS.between(base, e);

    long hours = diffSeconds / 3600;
    long minutes = (diffSeconds % 3600) / 60;
    long seconds = diffSeconds % 60;

    // 4. 如果小时超过24，进位到天（理论上不会，因为 base 已对齐日期）
    // 但为了健壮性，可以处理
    if (hours >= 24) {
      days += hours / 24;
      hours = hours % 24;
    }

    // 5. 构建结果
    StringBuilder sb = new StringBuilder();
    if (years > 0) {
      sb.append(years).append("年");
    }
    if (days > 0) {
      sb.append(days).append("天");
    }
    if (hours > 0 || sb.length() > 0) {
      sb.append(hours).append("小时");
    }
    if (minutes > 0 || sb.length() > 0) {
      sb.append(minutes).append("分钟");
    }
    if (seconds > 0 || sb.length() > 0) {
      sb.append(seconds).append("秒");
    }

    return sb.toString();
  }

  /**
   * MD 格式：月 + 天（忽略年，按月累计）
   * <p>
   * ✅ 闰年处理：
   * 虽然“月”是主要单位，但“天数”部分受闰年影响。
   * 例如：从 2020-01-01 到 2020-02-01，相差 31 天（1月有31天）。
   * 从 2020-02-01 到 2020-03-01，相差 29 天（闰年2月有29天）。
   * ChronoUnit.DAYS.between() 会自动计算实际天数，无需额外处理。
   */
  private static String md(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }

    LocalDate startD = s.toLocalDate();
    LocalDate endD = e.toLocalDate();
    int months = 0;
    LocalDate temp = startD;

    while (temp.plusMonths(1).isBefore(endD) || temp.plusMonths(1).isEqual(endD)) {
      months++;
      temp = temp.plusMonths(1);
    }

    long daysDiff = ChronoUnit.DAYS.between(temp, endD);
    int days = (int) daysDiff;

    StringBuilder sb = new StringBuilder();
    if (months > 0) {
      sb.append(months).append("个月");
    }
    if (days > 0) {
      sb.append(days).append("天");
    }
    return sb.toString();
  }

  /**
   * HMS 格式：小时 + 分钟 + 秒
   * <p>
   * ✅ 闰年处理：
   * 闰年不影响小时、分钟、秒的计算，因为 ChronoUnit.SECONDS.between()
   * 是基于精确时间戳的，已经包含了闰年带来的额外一天（86400秒）。
   * 例如：跨过 2020-02-29 的时间段会自动多出 86400 秒。
   */
  private static String hms(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }

    long totalSeconds = ChronoUnit.SECONDS.between(s, e);
    long hours = totalSeconds / 3600;
    long minutes = (totalSeconds % 3600) / 60;
    long seconds = totalSeconds % 60;

    StringBuilder sb = new StringBuilder();
    if (hours > 0) {
      sb.append(hours).append("小时");
    }
    if (minutes > 0 || sb.length() > 0) {
      sb.append(minutes).append("分钟");
    }
    if (seconds > 0 || sb.length() > 0) {
      sb.append(seconds).append("秒");
    }
    return sb.toString();
  }

  /**
   * WDHMS 格式：周 + 天 + 小时 + 分钟 + 秒
   * <p>
   * ✅ 闰年处理：
   * 与 HMS 相同，ChronoUnit.SECONDS.between() 基于精确时间差，
   * 自动包含闰年多出的一天（86400秒）。
   * 因此，周、天等单位的计算也自动准确。
   */
  private static String wdhms(Date start, Date end) {
    LocalDateTime s = toLocalDateTime(start);
    LocalDateTime e = (end == null) ? LocalDateTime.now() : toLocalDateTime(end);
    if (s.isAfter(e)) {
      log.warn("start date is after end date,{},{}", s, e);
      return "";
    }

    long totalSeconds = ChronoUnit.SECONDS.between(s, e);
    long weeks = totalSeconds / (7 * 24 * 3600);
    long days = (totalSeconds % (7 * 24 * 3600)) / (24 * 3600);
    long hours = (totalSeconds % (24 * 3600)) / 3600;
    long minutes = (totalSeconds % 3600) / 60;
    long seconds = totalSeconds % 60;

    StringBuilder sb = new StringBuilder();
    if (weeks > 0) {
      sb.append(weeks).append("周");
    }
    if (days > 0) {
      sb.append(days).append("天");
    }
    if (hours > 0 || sb.length() > 0) {
      sb.append(hours).append("小时");
    }
    if (minutes > 0 || sb.length() > 0) {
      sb.append(minutes).append("分钟");
    }
    if (seconds > 0 || sb.length() > 0) {
      sb.append(seconds).append("秒");
    }
    return sb.toString();
  }


  /**
   * 将 java.util.Date 转换为 LocalDateTime（使用系统默认时区）
   *
   * @param date 待转换的 Date 对象
   * @return 对应的 LocalDateTime
   */
  private static LocalDateTime toLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }


}
