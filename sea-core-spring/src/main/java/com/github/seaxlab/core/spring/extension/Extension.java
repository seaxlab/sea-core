package com.github.seaxlab.core.spring.extension;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * module name
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

  String bizId() default BizScenario.DEFAULT_BIZ_ID;

  String useCase() default BizScenario.DEFAULT_USE_CASE;

  String scenario() default BizScenario.DEFAULT_SCENARIO;
}