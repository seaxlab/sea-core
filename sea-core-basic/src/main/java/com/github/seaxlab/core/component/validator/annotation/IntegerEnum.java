package com.github.seaxlab.core.component.validator.annotation;

import com.github.seaxlab.core.component.validator.constraint.IntegerEnumValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Integer enum
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IntegerEnumValidator.class)
public @interface IntegerEnum {

    String message() default "参数必须为指定的值!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] values() default {};
}
