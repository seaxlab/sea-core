package com.github.seaxlab.core.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarDTO {
  //属性名称相同，但属性类型不同
  private Integer id;
  private String carName;
  private String make;
  private int seatCount;
  private String type;
  private List<String> names;
  private List<String> wheels;

}
