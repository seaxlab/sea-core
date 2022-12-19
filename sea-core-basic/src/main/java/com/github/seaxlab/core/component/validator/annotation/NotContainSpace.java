package com.github.seaxlab.core.component.validator.annotation;

import com.github.seaxlab.core.component.validator.constraint.NotContainSpaceValidator;
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
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotContainSpaceValidator.class)
public @interface NotContainSpace {

    String message() default "字段值不能包含空格";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
