package com.github.seaxlab.core.security.util;

import com.github.seaxlab.core.security.base62.Base62;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 与base64相比去掉了+/-=符号，0-9,A-Z,a-z
 * <p>
 * 常用于生成短链接、唯一 ID 等场景
 * </p>
 *
 * @author spy
 * @version 1.0 2021/5/4
 * @since 1.0
 */
@Slf4j
public class Base62Util {

  private static final Base62 CODEC = Base62.createInstanceWithGmpCharacterSet();

  /**
   * 编码
   *
   * @param val
   * @return
   */
  public static String encode(String val) {
    return new String(CODEC.encode(val.getBytes(StandardCharsets.UTF_8)));
  }

  /**
   * 解码
   *
   * @param val
   * @return
   */
  public static String decode(String val) {
    return new String(CODEC.decode(val.getBytes(StandardCharsets.UTF_8)));
  }

}
