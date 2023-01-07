package com.github.seaxlab.core.model.component.datetime;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.seaxlab.core.model.layer.dto.DTO;
import lombok.Data;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/26
 * @since 1.0
 */
@Data
public class DateTimeFullRange extends DTO {
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date begin;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date end;
}
