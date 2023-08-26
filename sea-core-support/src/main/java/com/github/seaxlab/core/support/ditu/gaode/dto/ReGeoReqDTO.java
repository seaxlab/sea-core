package com.github.seaxlab.core.support.ditu.gaode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * re geo Request DTO
 *
 * @author spy
 * @version 1.0 2023/08/26
 * @since 1.0
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ReGeoReqDTO extends BaseGaoDeReqDTO {

  //传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。
  private String location;

  // extend


}
