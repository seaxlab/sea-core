package com.github.seaxlab.core.spring.component.tunnel.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 执行event事件BO
 *
 * @author spy
 * @version 1.0 2025/6/26
 * @since 1.0
 */
@Data
public class BeanEventReqBO implements Serializable {
  // 参数封装
  private ExecuteArgumentInfoBO argument;

  // config
  private Boolean txFlag;
}
