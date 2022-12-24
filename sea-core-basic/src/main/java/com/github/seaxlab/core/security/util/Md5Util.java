package com.github.seaxlab.core.security.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class Md5Util {

  private Md5Util() {
  }

  /**
   * 获取MD5摘要
   *
   * @param str
   * @return
   */
  public static String getDigest(String str) {
    return DigestUtils.md5Hex(str);
  }

}
