package com.github.seaxlab.core.component.validator;

import com.github.seaxlab.core.component.validator.dto.UserAddRequestDTO;
import com.github.seaxlab.core.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class NotContainSpaceTest extends BaseValidatorTest {

  @Test
  public void test1() throws Exception {
    UserAddRequestDTO dto = new UserAddRequestDTO();
    dto.setName(" abc");

    Set<ConstraintViolation<UserAddRequestDTO>> set = ValidationUtil.validate(dto);
    checkValidator(set);
  }

  @Test
  public void test31() throws Exception {
    UserAddRequestDTO dto = new UserAddRequestDTO();

    Set<ConstraintViolation<UserAddRequestDTO>> set = ValidationUtil.validate(dto);
    checkValidator(set);
  }


}
