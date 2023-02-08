package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.enums.IErrorEnum;
import com.github.seaxlab.core.util.MessageUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * base exception
 *
 * @author spy
 * @version 1.0 2019/4/11
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseAppException extends RuntimeException {

  private String code; // NOSONAR

  private String desc; // NOSONAR

  // 可扩展参数

  public BaseAppException() {
    super();
  }

  public BaseAppException(Throwable throwable) {
    super(throwable);
  }

  public BaseAppException(String desc) {
    this("", desc);
  }

  public BaseAppException(String code, String desc) {
    super((code != null) ? (code + (desc == null ? "" : (":" + desc))) : desc);
    this.code = code;
    this.desc = desc;
  }

  public BaseAppException(IErrorEnum errorEnum) {
    this.code = errorEnum.getCode();
    this.desc = errorEnum.getMessage();
  }

  public BaseAppException(IErrorEnum errorEnum, Object... args) {
    this.code = errorEnum.getCode();
    this.desc = MessageUtil.format(errorEnum.getMessage(), args);
  }

}
