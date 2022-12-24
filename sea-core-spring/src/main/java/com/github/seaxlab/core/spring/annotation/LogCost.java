package com.github.seaxlab.core.spring.annotation;

import java.lang.annotation.*;

/**
 * 记录耗时，支持接口和普通类
 *
 * @author spy
 * @version 1.0 2021/12/15
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface LogCost {

  /**
   * business category
   *
   * @return
   */
  String type() default "";

}
