package com.github.seaxlab.core.http.dto.response;

import lombok.Data;

/**
 * http upload resp DTO
 *
 * @author spy
 * @version 1.0 2021/3/29
 * @since 1.0
 */
@Data
public class HttpUploadRespDTO {

  /**
   * 服务端响应内容（目前），具体内容，由调用方来反序列化成对象
   */
  private String content;
}
