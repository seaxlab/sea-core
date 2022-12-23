package com.github.seaxlab.core.model.component.person;

import java.io.Serializable;
import lombok.Data;

/**
 * 籍贯
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
@Data
public class NativePlace implements Serializable {

  //省
  private String provinceCode;
  private String provinceName;
  //市
  private String cityCode;
  private String cityName;
  //区/县
  private String countyCode;
  private String countyName;
  //
  private String address;
}
