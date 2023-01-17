package com.github.seaxlab.core.spring.component.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * extension annotation
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Extension {

  /**
   * 业务类型
   *
   * @return
   */
  String bizId() default BizScenario.DEFAULT_BIZ_ID;

  /**
   * 用例
   *
   * @return
   */
  String useCase() default BizScenario.DEFAULT_USE_CASE;

  /**
   * 场景
   *
   * @return
   */
  String scenario() default BizScenario.DEFAULT_SCENARIO;
}