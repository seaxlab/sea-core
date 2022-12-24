package com.github.seaxlab.core.spring.annotation;

import java.lang.annotation.*;

/**
 * print request and response log
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface LogRequest {

  /**
   * business category
   *
   * @return
   */
  String type() default "";
}
