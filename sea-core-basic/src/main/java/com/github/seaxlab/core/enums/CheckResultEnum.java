package com.github.seaxlab.core.enums;

/**
 * 校验返回值
 *
 * @author spy
 * @version 1.0 2025/10/14
 * @since 1.0
 */
public enum CheckResultEnum {
  NORMAL, // 正常
  IDEMPOTENT, // 幂等
  EXCEPTION // 异常
}