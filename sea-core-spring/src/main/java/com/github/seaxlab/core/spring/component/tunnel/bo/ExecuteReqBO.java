package com.github.seaxlab.core.spring.component.tunnel.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * execute Request BO
 *
 * @author spy
 * @version 1.0 2024/08/31
 * @since 1.0
 */
@Data
public class ExecuteReqBO implements Serializable {

  private String service;
  // method or field
  private String method;
  private String field;
  //
  private List<ExecuteArgumentInfoBO> arguments;

  // config
  private Boolean txFlag;


}
