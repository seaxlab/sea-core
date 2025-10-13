package com.github.seaxlab.core.component.layer.encrypt;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加密请求
 * <p>请求签名</p>
 * <p>内容加密</p>
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncryptRequestDTO implements Serializable {

  /**
   * 服务商提供的appId
   */
  @NotEmpty(message = "appId不能为空")
  private String appId;

  /**
   *
   */
  @NotEmpty(message = "请求内容不能为空")
  private String bizContent;

  /**
   * 支持的加密版本
   */
  @NotEmpty(message = "version不能为空")
  private String version;

  /**
   * 请求时间戳 yyyyMMddHHmmss
   */
  @NotEmpty(message = "timestamp不能为空")
  private String timestamp;

  /**
   * 请求签名值
   */
  @NotEmpty(message = "sign不能为空")
  private String sign;

}
