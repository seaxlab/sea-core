package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.thread.util.ThreadContextUtil;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

/**
 * log util
 *
 * @author spy
 * @version 1.0 2021/3/26
 * @since 1.0
 */
@Slf4j
public final class LogUtil {

  private static final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

  //------------- log can be switch begin -----------------------
  public static void info(boolean flag, String info) {
    if (flag) {
      log.info(info);
    }
  }

  public static void info(boolean flag, String format, Object arg) {
    if (flag) {
      log.info(format, arg);
    }
  }

  public static void info(boolean flag, String format, Object arg1, Object arg2) {
    if (flag) {
      log.info(format, arg1, arg2);
    }
  }

  public static void info(boolean flag, String format, Object... arguments) {
    if (flag) {
      log.info(format, arguments);
    }
  }

  public static void info(boolean flag, String msg, Throwable t) {
    if (flag) {
      log.info(msg, t);
    }
  }

  public static void warn(boolean flag, String msg) {
    if (flag) {
      log.warn(msg);
    }
  }

  public static void warn(boolean flag, String format, Object arg) {
    if (flag) {
      log.warn(format, arg);
    }
  }

  public static void warn(boolean flag, String format, Object arg1, Object arg2) {
    if (flag) {
      log.warn(format, arg1, arg2);
    }
  }

  public static void warn(boolean flag, String format, Object... arguments) {
    if (flag) {
      log.warn(format, arguments);
    }
  }

  public static void warn(boolean flag, String msg, Throwable t) {
    if (flag) {
      log.warn(msg, t);
    }
  }

  public void error(boolean flag, String msg) {
    if (flag) {
      log.error(msg);
    }
  }

  public void error(boolean flag, String format, Object arg) {
    if (flag) {
      log.error(format, arg);
    }
  }

  public void error(boolean flag, String format, Object arg1, Object arg2) {
    if (flag) {
      log.error(format, arg1, arg2);
    }
  }

  public void error(boolean flag, String format, Object... arguments) {
    if (flag) {
      log.error(format, arguments);
    }
  }

  public void error(boolean flag, String msg, Throwable t) {
    if (flag) {
      log.error(msg, t);
    }
  }

  //------------------log add prefix
  // 循环中，处理同种业务，但是是不同的主体
  public static void debugR(String format, Object... arguments) {
    if (log.isDebugEnabled()) {
      String message = MessageFormatter.arrayFormat(format, arguments).getMessage();
      log.debug("{},{}", ThreadContextUtil.getRequestNo(), message);
    }
  }

  public static void infoR(String format, Object... arguments) {
    if (log.isInfoEnabled()) {
      String message = MessageFormatter.arrayFormat(format, arguments).getMessage();
      log.info("{},{}", ThreadContextUtil.getRequestNo(), message);
    }
  }

  public static void warnR(String format, Object... arguments) {
    String message = MessageFormatter.arrayFormat(format, arguments).getMessage();
    log.info("{},{}", ThreadContextUtil.getRequestNo(), message);
  }

  public static void warnR(String message, Throwable t) {
    String msg = MessageUtil.format("{},{}", ThreadContextUtil.getRequestNo(), message);
    log.warn(msg, t);
  }

  public static void errorR(String format, Object... arguments) {
    String message = MessageFormatter.arrayFormat(format, arguments).getMessage();
    log.error("{},{}", ThreadContextUtil.getRequestNo(), message);
  }

  public static void errorR(String message, Throwable t) {
    String msg = MessageUtil.format("{},{}", ThreadContextUtil.getRequestNo(), message);
    log.error(msg, t);
  }
  //---------------------------------custom log

  public static void info(Logger log, boolean flag, String info) {
    if (flag) {
      log.info(info);
    }
  }

  public static void info(Logger log, boolean flag, String format, Object arg) {
    if (flag) {
      log.info(format, arg);
    }
  }

  public static void info(Logger log, boolean flag, String format, Object arg1, Object arg2) {
    if (flag) {
      log.info(format, arg1, arg2);
    }
  }

  public static void info(Logger log, boolean flag, String format, Object... arguments) {
    if (flag) {
      log.info(format, arguments);
    }
  }

  public static void info(Logger log, boolean flag, String msg, Throwable t) {
    if (flag) {
      log.info(msg, t);
    }
  }

  public static void warn(Logger log, boolean flag, String msg) {
    if (flag) {
      log.warn(msg);
    }
  }

  public static void warn(Logger log, boolean flag, String format, Object arg) {
    if (flag) {
      log.warn(format, arg);
    }
  }

  public static void warn(Logger log, boolean flag, String format, Object arg1, Object arg2) {
    if (flag) {
      log.warn(format, arg1, arg2);
    }
  }

  public static void warn(Logger log, boolean flag, String format, Object... arguments) {
    if (flag) {
      log.warn(format, arguments);
    }
  }

  public static void warn(Logger log, boolean flag, String msg, Throwable t) {
    if (flag) {
      log.warn(msg, t);
    }
  }


  public void error(Logger log, boolean flag, String msg) {
    if (flag) {
      log.error(msg);
    }
  }

  public void error(Logger log, boolean flag, String format, Object arg) {
    if (flag) {
      log.error(format, arg);
    }
  }

  public void error(Logger log, boolean flag, String format, Object arg1, Object arg2) {
    if (flag) {
      log.error(format, arg1, arg2);
    }
  }

  public void error(Logger log, boolean flag, String format, Object... arguments) {
    if (flag) {
      log.error(format, arguments);
    }
  }

  public void error(Logger log, boolean flag, String msg, Throwable t) {
    if (flag) {
      log.error(msg, t);
    }
  }

  //------------- log can be switch end -----------------------

  /**
   * dump content to custom dir.
   * <p>
   * 向指定目录输出日志dump文件 ${user.home}/logs/sea/${MODULE_NAME}/${yyyyMMdd_HHmmss}_${PID}_${MODULE_NAME}.log
   * </p>
   *
   * @param module  module name
   * @param content content
   */
  public static void dump(String module, String content) {
    if (StringUtil.isEmpty(module)) {
      log.warn("module is empty.", module);
      return;
    }

    if (StringUtil.isEmpty(content)) {
      log.warn("{} content is empty.", module);
      return;
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      FileUtil.writeFile(getLogFileNameInModule(module), content);
    } catch (Exception e) {
      log.error("fail to dump {} log. exception={}", module, e);
    } finally {
      log.info("dum {} log end. cost={}ms", module, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
  }

  /**
   * 10min一次dump
   *
   * @param module  module
   * @param content content.
   */
  public static void dumpByRate(String module, String content) {
    if (StringUtil.isEmpty(module)) {
      log.warn("module is empty.", module);
      return;
    }

    if (StringUtil.isEmpty(content)) {
      log.warn("{} content is empty.", module);
      return;
    }

    rateLimiterMap.computeIfAbsent(module, key -> {
      // 1 per 10min
      return RateLimiter.create(1.0 / (10 * 60));
    });

    RateLimiter rateLimiter = rateLimiterMap.get(module);

    boolean hasFlag = rateLimiter.tryAcquire(1);
    log.info("try acquire limiter flag={}", hasFlag);
    if (hasFlag) {
      Stopwatch stopwatch = Stopwatch.createStarted();
      try {
        FileUtil.writeFile(getLogFileNameInModule(module), content);
      } catch (Exception e) {
        log.error("fail to dump {} log. exception={}", module, e);
      } finally {
        log.info("dum {} log end. cost={}ms", module, stopwatch.elapsed(TimeUnit.MILLISECONDS));
      }
    }
  }

  /**
   * 生产
   *
   * @param module
   * @return
   */
  private static String getLogFileNameInModule(String module) {
    String logPath = PathUtil.join(PathUtil.getUserHome(), "logs", "sea", module);
    FileUtil.ensureDir(logPath);
    String nowStr = DateUtil.toString(new Date(), DateFormatEnum.yyyyMMdd_HHmmss);
    // basePath + "/" + datetime + "_" + pid + "_jstack.log"
    return MessageUtil.format("{}/{}_{}_{}.log", logPath, nowStr, JvmUtil.getPID(), module);
  }


  /**
   * sys.out.print table.
   *
   * @param headers headers
   * @param data    data
   */
  public static void printTable(List<String> headers, List data) {
    printTable(headers, data, 16);
  }

  /**
   * sys.out.print table.
   *
   * @param headers     table header
   * @param data        data
   * @param columnWidth column width
   */
  public static void printTable(List<String> headers, List data, int columnWidth) {
    String format = "%" + columnWidth + "s|";
    for (String header : headers) {
      System.out.printf(format, header);
    }
    System.out.println("");
    for (String header : headers) {
      System.out.print(StringUtils.leftPad("|", columnWidth + 1, "_"));
    }
    System.out.println("");

    if (data == null || data.isEmpty()) {
      return;
    }

    data.stream().forEach(item -> {
      headers.stream().forEach(header -> {
        Object value = null;
        try {
          value = FieldUtils.readField(item, header, true);
        } catch (Exception e) {
          log.error("fail to read field.", e);
          value = "";
        }
        System.out.printf(format, value);
      });
      System.out.println("");
    });

  }
}
