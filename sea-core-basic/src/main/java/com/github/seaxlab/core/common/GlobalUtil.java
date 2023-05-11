package com.github.seaxlab.core.common;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * global util for project customized
 *
 * @author spy
 * @version 1.0 2023/04/20
 * @since 1.0
 */
@Slf4j
public final class GlobalUtil {

  private GlobalUtil() {
  }

  public static void throwLockFailException() {
    if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
      throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
    }

    ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
  }

  public static void checkDB(int rowCount, String remark) {
    if (rowCount > 0) {
      log.info("row count={}, {}", rowCount, remark);
    } else {
      log.warn("row count={}, {}", rowCount, remark);
      throwCheckDBException();
    }
  }

  public static void throwCheckDBException() {
    if (SeaGlobalConfig.EXCEPTION_DB_UPDATE_FAIL != null) {
      throw SeaGlobalConfig.EXCEPTION_DB_UPDATE_FAIL;
    }
    ExceptionHandler.publish(ErrorMessageEnum.DB_OPERATION_FAIL);

  }

}
