package com.github.seaxlab.core.spring.validator.annotation;

import com.github.seaxlab.core.spring.validator.constraint.BeanCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * bean check
 * 基于hibernate-validator实现基本参数的校验
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BeanCheckValidator.class)
public @interface BeanCheck {
  String message() default "参数必须为指定的值!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String value() default "";
}
