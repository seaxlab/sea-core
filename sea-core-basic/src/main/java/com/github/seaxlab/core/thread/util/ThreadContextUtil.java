package com.github.seaxlab.core.thread.util;

import com.github.seaxlab.core.exception.Precondition;
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

  private static final String REQUEST_NO = "sea_request_no";

  private ThreadContextUtil() {
  }

  /**
   * set biz request no
   *
   * @param requestNo request no
   */
  public static void setRequestNo(String requestNo) {
    Precondition.checkNotBlank(requestNo, "requestNo cannot be empty.");
    ThreadContext.put(REQUEST_NO, requestNo);
  }

  /**
   * get biz request no
   *
   * @return biz request no
   */
  public static String getRequestNo() {
    return ThreadContext.getSafe(REQUEST_NO, "");
  }
}
