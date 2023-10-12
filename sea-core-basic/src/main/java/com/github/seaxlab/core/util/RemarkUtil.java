package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.RemarkFormatEnum;
import com.github.seaxlab.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;

/**
 * remark util
 *
 * @author spy
 * @version 1.0 2023/10/12
 * @since 1.0
 */
@Slf4j
public final class RemarkUtil {

  /**
   * format
   * <p>
   * 支付失败(余额不足)
   * </p>
   *
   * @param main
   * @param description
   * @return
   */
  public static String format(String main, String description) {
    return format(main, description, RemarkFormatEnum.V1);
  }

  public static String format(String main, String description, RemarkFormatEnum formatEnum) {
    Precondition.checkNotNull(formatEnum, "format不能为空");
    //
    return format(main, description, formatEnum.getCode());
  }

  public static String format(String main, String description, String format) {
    Precondition.checkNotBlank(format, "format不能为空");
    //
    if (StringUtil.isBlank(description)) {
      return StringUtil.defaultIfEmpty(main);
    }
    return StringUtil.format(format, StringUtil.defaultIfEmpty(main), description);
  }

}
