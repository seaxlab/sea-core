# enums

## 值类型

````
TEXT(1, "text", "文本", ""),
  NUMBER(2, "number", "数字", ""),
  BOOLEAN(3, "boolean", "布尔", ""),
  JSON(4, "json", "json", ""),
  MOBILE(5, "mobile", "手机号", ""),
  EMAIL(6, "email", "email", ""),
  MONEY(7, "money", "钱", ""),

  DATETIME(40, "datetime", "日期时间", "yyyy-MM-dd HH:mm:ss"),
  DATETIME_RANGE(41, "datetime_range", "日期时间范围", ""),

  DATE(42, "date", "日期", "yyyy-MM-dd"),
  DATE_RANGE(43, "date_range", "日期范围", ""),

  DATE_MMDD(44, "date_month_and_day", "月份和天", "MM-dd"),
  DATE_MMDD_RANGE(45, "date_month_and_day_range", "月份和天范围", ""),

  TIME(46, "time", "时间", "HH:mm:ss"),
  TIME_RANGE(47, "time_range", "时间范围", ""),

  TIME_HHMM(48, "time", "时分", "HH:mm"),
  TIME_HHMM_RANGE(49, "time_range", "时分范围", ""),
````

## 审核状态

````
INIT(10, "初始化"), // 一种不可见状态
  PENDING(20, "审批中"),
  APPROVED(30, "通过"),
  REJECTED(40, "拒绝"),
````

## 统计周期

````
DAY(10, "天"),
  WEEK(20, "周"),
  WEEK_CURRENT(21, "本周"),
  MONTH(30, "月"),
  MONTH_CURRENT(31, "本月"),
  YEAR(40, "年"),
  YEAR_CURRENT(41, "本年"),
  TIME_SEGMENT(50, "时间段"),
````