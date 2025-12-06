package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

/**
 * byte unit util
 * <p>
 * GB,MB,KB,B
 * </p>
 *
 * @author spy
 * @version 1.0 2019-08-13
 * @since 1.0
 */
@Slf4j
public final class ByteUnitUtil {

  //获取到的size为：1705230

  public static final int GB = 1024 * 1024 * 1024;

  public static final int MB = 1024 * 1024;

  public static final int KB = 1024;

  private ByteUnitUtil() {
  }

  /**
   * no point
   *
   * @param size
   * @return
   */
  public static String format(long size) {


    return format(size, 0);
  }

  /**
   * custom show
   *
   * @param size
   * @param pointCount must be >=0
   * @return
   */
  public static String format(long size, int pointCount) {

    if (pointCount < 0) {
      throw new IllegalArgumentException("pointCount must be >=0");
    }

    String pattern;
    if (pointCount == 0) {
      pattern = "0";
    } else {
      pattern = "0.";
      pattern = StringUtils.rightPad(pattern, pointCount + pattern.length(), "0");
    }

    DecimalFormat df = new DecimalFormat(pattern);

    String result;

    if (size / GB >= 1) {
      //如果当前Byte的值大于等于1GB
      result = df.format(size / (float) GB) + "GB";
    } else if (size / MB >= 1) {
      //如果当前Byte的值大于等于1MB
      result = df.format(size / (float) MB) + "MB";
    } else if (size / KB >= 1) {
      //如果当前Byte的值大于等于1KB
      result = df.format(size / (float) KB) + "KB";
    } else {
      result = size + "B";
    }

    return result;

  }

}
