package com.github.seaxlab.core.model.util;

import com.github.seaxlab.core.model.Result;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

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
   * build from other result.
   *
   * @param otherResult
   * @param <T>
   * @return
   */
  public <T> Result<T> from(Result<T> otherResult) {
    if (otherResult == null) {
      log.warn("other result is empty.");
      return Result.fail();
    }
    Result<T> result = new Result<>();
    result.setCode(otherResult.getCode());
    result.setMsg(otherResult.getMsg());
    return result;
  }

  /**
   * check is ok.
   *
   * @return true/false
   */
  public static boolean isOk(Result<?> result) {
    if (Objects.isNull(result)) {
      log.warn("result is null");
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
  public static boolean isFail(Result<?> result) {
    if (Objects.isNull(result)) {
      log.warn("result is null");
      throw new NullPointerException("result is null");
    }

    if (result.getSuccess() != null) {
      return !result.getSuccess();
    }
    return true;
  }
}
