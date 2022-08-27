package com.github.seaxlab.core.component.validator.annotation;

import com.github.seaxlab.core.component.validator.constraint.CodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * code check
 * <p>
 * 字母+数字+中划线+下划线
 * </p>
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CodeValidator.class)
public @interface CodeCheck {

    String message() default "编码不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
