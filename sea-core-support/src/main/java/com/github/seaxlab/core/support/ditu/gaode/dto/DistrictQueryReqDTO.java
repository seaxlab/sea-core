package com.github.seaxlab.core.support.ditu.gaode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * district query Request DTO
 *
 * @author spy
 * @version 1.0 2023/08/26
 * @since 1.0
 */
@Data
@JsonInclude(Include.NON_NULL)
public class DistrictQueryReqDTO extends BaseGaoDeReqDTO {

  /**
   * 查询关键字
   */
  private String keywords;

  /**
   * 子级行政区 规则：设置显示下级行政区级数（行政区级别包括：国家、省/直辖市、市、区/县、乡镇/街道多级数据）
   * <p>
   * 可选值：0、1、2、3等数字，并以此类推
   * <p>
   * 0：不返回下级行政区；
   * <p>
   * 1：返回下一级行政区；
   * <p>
   * 2：返回下两级行政区；
   * <p>
   * 3：返回下三级行政区；
   * <p>
   * 需要在此特殊说明，目前部分城市和省直辖县因为没有区县的概念，故在市级下方直接显示街道。
   * <p>
   * 例如：广东-东莞、海南-文昌市
   */
  private Integer subdistrict = 1;

  private Integer page = 1;

  private Integer offset = 20;

  /**
   * filter
   * <p>
   * 根据区划过滤
   * <p>
   * 按照指定行政区划进行过滤，填入后则只返回该省/直辖市信息
   * <p>
   * 需填入adcode，为了保证数据的正确，强烈建议填入此参数
   */
  private String filter;

}
