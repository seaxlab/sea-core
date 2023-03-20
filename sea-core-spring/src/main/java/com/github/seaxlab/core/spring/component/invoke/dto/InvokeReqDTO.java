package com.github.seaxlab.core.spring.component.invoke.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * invoke Request DTO
 *
 * @author spy
 * @version 1.0 2023/03/20
 * @since 1.0
 */
@Data
public class InvokeReqDTO {

  @NotEmpty
  private String service;
  @NotEmpty
  private String method;
  //
  private String argument;
}
