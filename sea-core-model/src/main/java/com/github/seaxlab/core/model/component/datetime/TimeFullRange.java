package com.github.seaxlab.core.model.component.datetime;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.seaxlab.core.model.layer.dto.DTO;
import java.util.Date;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/26
 * @since 1.0
 */
@Data
public class TimeFullRange extends DTO {

  @JSONField(format = "HH:mm:ss")
  private Date begin;

  @JSONField(format = "HH:mm:ss")
  private Date end;
}
