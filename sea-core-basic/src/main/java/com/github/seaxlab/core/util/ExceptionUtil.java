package com.github.seaxlab.core.util;

import java.lang.reflect.InvocationTargetException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/5
 * @since 1.0
 */
@Slf4j
public final class ExceptionUtil {

  /**
   * 默认前3行异常
   */
  public static final int DEFAULT_TOP_N = 3;

  private ExceptionUtil() {

  }

  /**
   * get message
   *
   * @param throwable throwable
   * @return String
   */
  public static String getMessage(final Throwable throwable) {
    return ExceptionUtils.getMessage(throwable);
  }

  /**
   * 获取堆栈信息 InvocationTargetException中Message为null，需要使用此方法来获取
   *
   * @param throwable throwable
   * @return String
   */
  public static String getStackTrace(final Throwable throwable) {
    return ExceptionUtils.getStackTrace(throwable);
  }

  /**
   * 获取最底层的异常,非常重要的接口 排除 InvocationTargetException/NestedServletException等嵌套异常
   *
   * @param throwable throwable
   * @return Throwable
   */
  public static Throwable getRootCause(final Throwable throwable) {
    return ExceptionUtils.getRootCause(throwable);
  }

  /**
   * 获取跟路径堆栈信息
   *
   * @param throwable throwable
   * @return String
   */
  public static String getRootCauseMessage(final Throwable throwable) {
    return ExceptionUtils.getRootCauseMessage(throwable);
  }


  /**
   * 获取第一行 异常信息
   *
   * @param t throwable
   * @return String
   */
  public static String getMsg1(Throwable t) {
    return getMsgN(t, 1);
  }

  /**
   * 获取前两行 异常信息
   *
   * @param t throwable
   * @return String
   */
  public static String getMsg2(Throwable t) {
    return getMsgN(t, 2);
  }

  /**
   * 获取前3行信息
   *
   * @param t throwable
   * @return String
   */
  public static String getMsg3(Throwable t) {
    return getMsgN(t, 3);
  }

  /**
   * 获取前多少行信息
   *
   * @param t     throwable
   * @param limit limit line size
   * @return string
   */
  public static String getMsgN(Throwable t, int limit) {
    if (t == null) {
      return StringUtil.EMPTY;
    }

    limit = limit <= 0 ? DEFAULT_TOP_N : limit;

    String shortMsg = "";

    // org.springframework.web.util.NestedServletException;
//        if (t instanceof NestedServletException) {
//            NestedServletException e = (NestedServletException) t;
//
//            if (e.getCause() != null) {
//                if (e.getCause() instanceof InvocationTargetException) {
//                    shortMsg = checkInvocationTargetException(e.getCause());
//                } else {
//                    shortMsg = getShortMsg(e.getCause());
//                }
//            } else {
//                shortMsg = getShortMsg(e);
//            }
//        } else
    if (t instanceof InvocationTargetException) {
      InvocationTargetException invocationTargetException = (InvocationTargetException) t;
      Throwable target = invocationTargetException.getTargetException();
      if (target != null) {
        shortMsg = getShortMsg(target, limit);
      } else {
        log.warn("invocation target exception target is null");
        shortMsg = getShortMsg(t, limit);
      }
    } else {
      shortMsg = getShortMsg(t, limit);
    }

    return shortMsg;
  }

  /**
   * remove invocation
   *
   * @param t
   * @return
   */
  public static Throwable removeInvocation(Throwable t) {
    if (t instanceof InvocationTargetException) {
      return ((InvocationTargetException) t).getTargetException();
    }

    return t;
  }


  //--------------------------------- private --------------------------------------------
  private static String getShortMsg(Throwable t, int limit) {
    if (t == null) {
      return StringUtils.EMPTY;
    }
    limit = limit <= 0 ? DEFAULT_TOP_N : limit;

    String shortMsg = StringUtils.EMPTY;

    try {
      String stackMsg = ExceptionUtils.getStackTrace(t);
      stackMsg = StringUtils.defaultIfEmpty(stackMsg, StringUtils.EMPTY);

      // 这里最后一样会包含剩余所有的异常，因此+1
      String[] msgArray = stackMsg.split(System.lineSeparator(), limit + 1);
      int size = Math.min(msgArray.length, limit);

      StringBuilder strBuilder = new StringBuilder();
      for (int i = 0; i < size; i++) {
        strBuilder.append(msgArray[i]);
        if ((i + 1) == size) {
          // last one
        } else {
          strBuilder.append("\n");
        }
      }
      shortMsg = strBuilder.toString();
    } catch (Exception e) {
      log.error("fail to get short msg of stack exception.");
    }

    return shortMsg;
  }

  private static String checkInvocationTargetException(Throwable t) {
    String shortMsg = StringUtils.EMPTY;
    if (t == null) {
      return shortMsg;
    }

    if (t instanceof InvocationTargetException) {
      InvocationTargetException invocationTargetException = (InvocationTargetException) t;
      Throwable target = invocationTargetException.getTargetException();
      if (target != null) {
        shortMsg = getShortMsg(target, 3);
      } else {
        log.warn("invocation target exception target is null");
      }
    }

    return shortMsg;
  }


}
