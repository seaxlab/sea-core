package com.github.seaxlab.core.model.util;

import com.github.seaxlab.core.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * result util
 *
 * @author spy
 * @version 1.0 2023/03/16
 * @since 1.0
 */
@Slf4j
public final class ResultUtil {

  private ResultUtil() {
  }

  /**
   * check is ok.
   *
   * @return true/false
   */
  public boolean isOk(Result result) {
    if (Objects.isNull(result)) {
      throw new NullPointerException("result is null");
    }
    if (result.getSuccess() != null) {
      return result.getSuccess();
    }
    // warning!! default is false.
    return false;
  }

  /**
   * check is fail.
   *
   * @return true/false
   */
  public boolean isFail(Result result) {
    if (Objects.isNull(result)) {
      throw new NullPointerException("result is null");
    }

    if (result.getSuccess() != null) {
      return !result.getSuccess();
    }
    return true;
  }
}
