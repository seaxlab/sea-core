package com.github.seaxlab.core.support.notify.dto;

import lombok.Data;

import java.util.Map;

/**
 * base notify DTO.
 *
 * @author spy
 * @version 1.0 2020/3/4
 * @since 1.0
 */
@Data
public class BaseNotifyDTO {

  // title+content or template+param
  //
  private String title;
  private String content;

  // {name},你好
  private String template;
  private Map<String, String> param;
}
