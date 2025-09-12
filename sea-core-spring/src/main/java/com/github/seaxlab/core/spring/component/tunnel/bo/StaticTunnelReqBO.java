package com.github.seaxlab.core.spring.component.tunnel.bo;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/4/3
 * @since 1.0
 */
@Data
public class StaticTunnelReqBO {

  //
  private String className;
  // method or field
  private String method;
  private String field;
  //
  private List<ExecuteArgumentInfoBO> arguments;
}
