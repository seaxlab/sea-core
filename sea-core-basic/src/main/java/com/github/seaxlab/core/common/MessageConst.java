package com.github.seaxlab.core.common;

import lombok.extern.slf4j.Slf4j;

/**
 * message const util
 *
 * @author spy
 * @version 1.0 2023/08/15
 * @since 1.0
 */
@Slf4j
public final class MessageConst {

  private MessageConst() {
  }

  public static final String SYS_INTERRUPTED_EXCEPTION = "interrupted exception";
  public static final String SYS_FUTURE_GET_FAIL = "fail to future.get()";
}
