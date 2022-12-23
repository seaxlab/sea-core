package com.github.seaxlab.core.model.layer.vo;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/4
 * @since 1.0
 */
@Data
public class SimpleVO extends VO {

  private Long id;
  private String code;
  private String name;
  private String type;
  private String typeName;
  private String remark;
}
