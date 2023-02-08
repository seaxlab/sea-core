package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.enums.IErrorEnum;

/**
 * business exception for business
 *
 * @author spy
 * @version 1.0 2023/2/8
 * @since 1.0
 */
public class BusinessException extends BaseAppException {

  public BusinessException(IErrorEnum errorEnum) {
    super(errorEnum);
  }

  public BusinessException(IErrorEnum errorEnum, Object... args) {
    super(errorEnum, args);
  }
}
