package com.github.seaxlab.core.component.validator;

import com.github.seaxlab.core.component.validator.dto.UserAddRequestDTO;
import com.github.seaxlab.core.util.ValidationUtil;
import java.util.Set;
import javax.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/12/19
 * @since 1.0
 */
@Slf4j
public class NotNullStringTest extends BaseValidatorTest {

  @Test
  public void test16() throws Exception {
    UserAddRequestDTO dto = new UserAddRequestDTO();
    dto.setName("null");
    Set<ConstraintViolation<UserAddRequestDTO>> set = ValidationUtil.validate(dto);
    checkValidator(set);
  }
}
