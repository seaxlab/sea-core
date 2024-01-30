package com.github.seaxlab.core.http.dto;

import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import lombok.EqualsAndHashCode;

/**
 * http upload DTO
 *
 * @author spy
 * @version 1.0 2021/3/29
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpUploadDTO extends BaseHttpDTO {

  // file/stream二选一即可

  /**
   * 常规字段
   */
  private Map<String, Object> textFieldMap;

  /**
   * 文件字段
   */
  private Map<String, File> fileFieldMap;

  /**
   * 流方式字段
   */
  private Map<String, InputStream> streamFieldMap;
}
