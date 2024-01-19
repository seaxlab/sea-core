package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.enums.IErrorEnum;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;

/**
 * 异常抛出辅助类
 *
 * @author spy
 * @version 1.0 2018/4/11
 * @since 1.0
 */
public final class ExceptionHandler {

  private ExceptionHandler() {
  }

  public static final String NO_ERROR_CODE = "";

  /**
   * publish exception(recommend)
   *
   * @param exception
   */
  public static void publish(IErrorEnum exception) {
    throw new BaseAppException(exception);
  }

  /**
   * publish exception(recommend)
   *
   * @param exception
   */
  public static void publish(IErrorEnum exception, Object... args) {
    throw new BaseAppException(exception, args);
  }

  public static void publish(String code, String msg) throws BaseAppException {
    publish(code, msg, null);
  }

  public static void publishMsg(String msg) throws BaseAppException {
    publish(NO_ERROR_CODE, msg, null);
  }

  public static void publishMsg(String format, Object... argArray) {
    FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
    publish(NO_ERROR_CODE, ft.getMessage(), null);
  }

  /**
   * 抛出异常
   *
   * @param code 错误码
   * @param msg  消息
   * @param t    Throwable
   * @throws BaseAppException base app exception
   */
  public static void publish(String code, String msg, Throwable t) throws BaseAppException {
    BaseAppException baseAppException;
    //
    if (t instanceof BaseAppException) {
      baseAppException = (BaseAppException) t;
    } else if (t instanceof InvocationTargetException) {
      // 仅仅对此情况进行处理，不能进行深层检查！
      Throwable cause = t.getCause();
      if (cause instanceof BaseAppException) {
        baseAppException = (BaseAppException) cause;
      } else {
        baseAppException = new BaseAppException(code, msg);
      }
    } else {
      baseAppException = new BaseAppException(code, msg);
    }

    throw baseAppException;
  }
}
