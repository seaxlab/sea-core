package com.github.seaxlab.core.component.validator.annotation;

import com.github.seaxlab.core.component.validator.constraint.NotNullStringValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * not contain space
 *
 * @author spy
 * @version 1.0 2022/12/29
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullStringValidator.class)
public @interface NotNullString {

  String message() default "字段值不能为null字符串";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
