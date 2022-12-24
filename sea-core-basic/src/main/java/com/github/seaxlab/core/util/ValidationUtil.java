package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * validation util
 *
 * @author spy
 * @version 1.0 2020/8/12
 * @since 1.0
 */
@Slf4j
public final class ValidationUtil {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * get default validate
   *
   * @return
   */
//    public static Validator getValidator() {
//        if (validator == null) {
//            ValidatorFactory validatorFactory = ;
//            validator = validatorFactory.getValidator();
//        }
//        return validator;
//    }

//    static {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//    }

  /**
   * 校验实体类
   *
   * @param t   实体
   * @param <T>
   * @return Set, 始终不为null
   */
  public static <T> Set<ConstraintViolation<T>> validate(T t) {
    return validator.validate(t);
//        if (constraintViolations.size() > 0) {
//            StringBuilder validateError = new StringBuilder();
//            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
//                validateError.append(constraintViolation.getMessage()).append(";");
//            }
//
//            throw new ValidationException(validateError.toString());
//        } else {
//            return Collections.emptySet();
//        }
  }

  /**
   * 通过组来校验实体类
   *
   * @param t      实体
   * @param groups 组
   * @param <T>
   * @return Set, 始终不为null
   */
  public static <T> Set<ConstraintViolation<T>> validate(T t, Class<?>... groups) {
    return validator.validate(t, groups);
//        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
//        if (constraintViolations.size() > 0) {
//            StringBuilder validateError = new StringBuilder();
//            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
//                validateError.append(constraintViolation.getMessage()).append(";");
//            }
//
//            throw new ValidationException(validateError.toString());
//        }
  }


}
