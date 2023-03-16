package com.github.seaxlab.core.model.component.datetime;

import com.github.seaxlab.core.model.checker.SimpleValidator;
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
public class TimeRange extends DTO implements SimpleValidator {

  //  @JSONField(format = "HH:mm")
  private Date begin;

  //  @JSONField(format = "HH:mm")
  private Date end;

  @Override
//  @JSONField(serialize = false)
  public boolean isValid() {
    //TODO
    return false;
  }

  public static TimeRange of(String beginStr, String endStr) {
    //TODO
    return null;
  }

  public static TimeRange of(Date begin, Date end) {
    //TODO
    return null;
  }


}
