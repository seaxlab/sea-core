package com.github.seaxlab.core.thread.util;

import com.github.seaxlab.core.common.CoreConst;
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

  private static final String BIZ_REQUEST_NO = "sea_biz_request_no";

  private ThreadContextUtil() {
  }

  /**
   * set biz request no
   *
   * @param requestNo request no
   */
  public static void setRequestNo(String requestNo) {
    Precondition.checkNotBlank(requestNo, "requestNo cannot be empty.");
    ThreadContext.put(BIZ_REQUEST_NO, requestNo);
  }

  /**
   * get biz request no
   *
   * @return biz request no
   */
  public static String getRequestNo() {
    return ThreadContext.getSafe(BIZ_REQUEST_NO, CoreConst.NOT_APPLICABLE);
  }
}
