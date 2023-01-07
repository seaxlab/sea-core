package com.github.seaxlab.core.spring.aop.enums;

import com.github.seaxlab.core.enums.IBaseEnum;
import lombok.Getter;

/**
 * aop expression enum
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Getter
public enum AopExpressionEnum implements IBaseEnum<String> {
  //
  EXECUTION_PACKAGE("execution(public * {}.*.*(..))", "包中public方法"), //
  EXECUTION_PACKAGE_AND_SUB("execution(public * {}..*.*(..))", "包及子包中public方法"),
  //
  ;
  private final String code;
  private final String desc;

  AopExpressionEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }


}