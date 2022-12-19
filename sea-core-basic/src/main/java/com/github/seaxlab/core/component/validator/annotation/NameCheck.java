package com.github.seaxlab.core.component.validator.annotation;

import com.github.seaxlab.core.component.validator.constraint.NameValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * name check
 *
 * <p>
 * 中文+字母+数字+中划线+下划线
 * </p>
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameValidator.class)
public @interface NameCheck {

    String message() default "名称不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
