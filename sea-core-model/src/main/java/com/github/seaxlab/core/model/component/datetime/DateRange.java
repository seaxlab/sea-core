package com.github.seaxlab.core.model.component.datetime;

import com.github.seaxlab.core.model.layer.dto.DTO;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/26
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class DateRange extends DTO {

  //  @JSONField(format = "yyyy-MM-dd")
  private Date begin;

  //  @JSONField(format = "yyyy-MM-dd")
  private Date end;
}
