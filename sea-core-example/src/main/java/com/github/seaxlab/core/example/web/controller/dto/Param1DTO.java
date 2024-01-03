package com.github.seaxlab.core.example.web.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Data
public class Param1DTO {
  //@NotEmpty  hibernate的正常校验
  private String code;
  private List<String> roles;
}
