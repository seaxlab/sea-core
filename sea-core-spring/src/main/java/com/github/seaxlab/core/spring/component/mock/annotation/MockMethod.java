package com.github.seaxlab.core.spring.component.mock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mock method
 *
 * @author spy
 * @version 1.0 2023/11/2
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface MockMethod {

  /**
   * property key in system env.
   *
   * @return
   */
  String key();

  /**
   * mock class
   *
   * @return
   */
  Class<?> clazz();

  /**
   * mock class method
   *
   * @return
   */
  String method() default "";
}
