package com.github.seaxlab.core.dal.util;

import com.github.seaxlab.core.enums.IErrorEnum;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

/**
 * dal util for check row count
 *
 * @author spy
 * @version 1.0 2022/10/14
 * @since 1.0
 */
@Slf4j
public final class DalUtil {

  private DalUtil() {
  }

  /**
   * check db effect row count
   *
   * @param rowCount row count
   * @param remark   remark
   */
  public static void check(int rowCount, String remark) {
    log.info("row count={}, {}", rowCount, remark);
    if (rowCount <= 0) {
      throw new BaseAppException(ErrorMessageEnum.DB_OPERATION_FAIL);
    }
  }

  /**
   * check db effect row count
   *
   * @param rowCount  row count
   * @param remark    remark
   * @param errorEnum error enum
   */
  public static void check(int rowCount, String remark, IErrorEnum errorEnum) {
    log.info("row count={}, {}", rowCount, remark);
    if (rowCount <= 0) {
      throw new BaseAppException(errorEnum);
    }
  }

  /**
   * check db effect row count
   *
   * @param rowCount row count
   * @param remark   remark
   * @param args     args
   */
  public static void checkF(int rowCount, String remark, Object... args) {
    String message = MessageFormatter.arrayFormat(remark, args).getMessage();
    log.info("row count={}, {}", rowCount, message);
    if (rowCount <= 0) {
      throw new BaseAppException(ErrorMessageEnum.DB_OPERATION_FAIL);
    }
  }

  /**
   * check db effect row count
   *
   * @param flag   flag
   * @param remark remark
   */
  public static void check(boolean flag, String remark) {
    log.info("check db flag={}, {}", flag, remark);
    if (!flag) {
      throw new BaseAppException(ErrorMessageEnum.DB_OPERATION_FAIL);
    }
  }

  /**
   * check db effect row count
   *
   * @param flag      flag
   * @param remark    remark
   * @param errorEnum error enum
   */
  public static void check(boolean flag, String remark, IErrorEnum errorEnum) {
    log.info("check db flag={}, {}", flag, remark);
    if (!flag) {
      throw new BaseAppException(errorEnum);
    }
  }

}
