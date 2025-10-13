package com.github.seaxlab.core.component.layer.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/4/28
 * @since 1.0
 */
@Data
public class BaseSignRequestDTO implements Serializable {

  /**
   * appId
   */
  private String appId;

  /**
   * app秘钥
   */
  private String appSecret;

  /**
   * 随机字符串
   */
  private String nonceStr;

  /**
   * 时间戳，单位秒
   * <code>
   * String date = String.valueOf(System.currentTimeMillis() / 1000);
   * </code>
   */
  private String timestamp;
}
