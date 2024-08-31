package com.github.seaxlab.core.spring.component.tunnel.bo;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2024/8/31
 * @since 1.0
 */
@Data
public class ExecuteArgumentInfoBO {

  /**
   * 类名，全路径
   */
  private String className;

  /**
   * 类值，JSON字符,或简单原型值
   */
  private Object value;
}
