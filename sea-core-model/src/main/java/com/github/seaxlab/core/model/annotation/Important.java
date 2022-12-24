package com.github.seaxlab.core.model.annotation;

import java.lang.annotation.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/9
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Important {

  /**
   * 重要内容
   *
   * @return
   */
  String value() default "";

  /**
   * 重要程度
   *
   * @return
   */
  int level() default 1;
}
