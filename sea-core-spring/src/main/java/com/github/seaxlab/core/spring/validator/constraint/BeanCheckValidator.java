package com.github.seaxlab.core.spring.validator.constraint;

import com.github.seaxlab.core.spring.context.SpringContextHolder;
import com.github.seaxlab.core.spring.validator.annotation.BeanCheck;
import com.github.seaxlab.core.spring.validator.model.SimpleArgumentValidator;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class BeanCheckValidator implements ConstraintValidator<BeanCheck, Object> {

    private String beanName;

    @Override
    public void initialize(BeanCheck constraintAnnotation) {
        this.beanName = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        SimpleArgumentValidator<Object> simpleArgumentValidator = SpringContextHolder.getBean(this.beanName);
        return simpleArgumentValidator.check(value);
    }
}
