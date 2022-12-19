package com.github.seaxlab.core.component.validator.dto;

import com.github.seaxlab.core.component.validator.annotation.NotContainSpace;
import com.github.seaxlab.core.component.validator.annotation.NotNullString;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Data
public class UserAddRequestDTO {

  @NotNull
  @NotContainSpace
  @NotNullString
  private String name;
}
