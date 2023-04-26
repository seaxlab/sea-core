package com.github.seaxlab.core.thread.util;

import com.github.seaxlab.core.model.common.ModelConst;
import com.github.seaxlab.core.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;

/**
 * thread context util
 *
 * @author spy
 * @version 1.0 2023/04/26
 * @since 1.0
 */
@Slf4j
public final class ThreadContextUtil {

  private ThreadContextUtil() {
  }

  public static String getRequestNo() {
    return ThreadContext.getSafe(ModelConst.REQUEST_NO, "");
  }
}
