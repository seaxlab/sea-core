package com.github.seaxlab.core.common;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * global util
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

}
