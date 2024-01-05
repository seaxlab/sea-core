package com.github.seaxlab.core.component.layer.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * 基本返回值基类，所有返回值继承
 *
 * @author spy
 * @version 1.0 2019-06-02
 * @since 1.0
 */
@Data
public class BaseResponseVO implements Serializable {

  // 幂等字段，上下游系统自行保证
  // idempotent
  // public boolean repeat;
}
