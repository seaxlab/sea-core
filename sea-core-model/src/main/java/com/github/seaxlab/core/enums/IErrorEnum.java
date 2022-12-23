package com.github.seaxlab.core.enums;

/**
 * error exception
 *
 * @author spy
 * @version 1.0 2021/8/28
 * @since 1.0
 */
public interface IErrorEnum {

  /**
   * error code
   *
   * @return
   */
  String getCode();

  /**
   * error message
   *
   * @return
   */
  String getMessage();
}
