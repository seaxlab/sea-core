package com.github.seaxlab.core.component.validator.constraint;

import com.github.seaxlab.core.common.SymbolConst;
import com.github.seaxlab.core.component.validator.annotation.NotNullString;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * not null string validator
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class NotNullStringValidator implements ConstraintValidator<NotNullString, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (value == null || value.isEmpty()) {
      return true;
    }
    return !value.equalsIgnoreCase(SymbolConst.NULL);
  }
}
