package com.github.seaxlab.core.support.ditu.gaode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import lombok.Data;

/**
 * truck plan Request DTO
 * </p>
 * <a
 * href="https://lbs.amap.com/api/logistic-service/guide/wagon_path/truck-route-plan-basis">truck-route-plan-basis</a>
 *
 * @author spy
 * @version 1.0 2023/08/26
 * @since 1.0
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TruckRoutePlanReqDTO extends BaseGaoDeReqDTO {

  /**
   * 填入规则：X,Y，采用","分隔，例如“ 117.500244, 40.417801 ”，小数点后不得超过6位
   */
  private String origin;

  /**
   * 填入规则：X,Y，采用","分隔，例如“ 117.500244, 40.417801 ”，小数点后不得超过6位。
   */
  private String destination;

  /**
   * 1，躲避拥堵：返回的结果考虑路况，尽量躲避拥堵而规划路径；对应导航SDK货导策略12；
   * <p>
   * 2，不走高速：返回的结果考虑路况，不走高速；对应导航SDK货导策略13；
   * <p>
   * 3，避免收费：返回的结果考虑路况，尽可能规划收费较低甚至免费的路径；对应导航SDK货导策略14；
   * <p>
   * 4，躲避拥堵+不走高速：返回的结果考虑路况，尽量躲避拥堵，并且不走高速；对应导航SDK货导策略15；
   * <p>
   * 5，避免收费+不走高速：返回的结果考虑路况，尽量不走高速，并且尽量规划收费较低甚至免费的路径结果；对应导航SDK货导策略16；
   * <p>
   * 6，躲避拥堵+避免收费：返回的结果考虑路况，尽量的躲避拥堵，并且规划收费较低甚至免费的路径结果；对应导航SDK货导策略17；
   * <p>
   * 7，躲避拥堵+避免收费+不走高速：返回的结果考虑路况，尽量躲避拥堵，规划收费较低甚至免费的路径结果，并且尽量不走高速路；对应导航SDK货导策略18；
   * <p>
   * 8，高速优先：返回的结果考虑路况，会优先选择高速路；对应导航SDK货导策略19；
   * <p>
   * 9，躲避拥堵+高速优先：返回的结果考虑路况，会优先考虑高速路，并且会考虑路况躲避拥堵；对应导航SDK货导策略20；
   * <p>
   * 10，无路况速度优先：基于历史的通行速度数据，不考虑当前路况的影响，返回速度优先的路；如果不需要路况干扰计算结果，推荐使用此策略；（导航SDK货导策略无对应，真实导航时均会考虑路况）
   * <p>
   * 11，高德推荐：返回的结果会考虑路况，躲避拥堵，速度优先以及费用优先；500Km规划以内会返回多条结果，500Km以外会返回单条结果；考虑路况情况下的综合最优策略，推荐使用；对应导航SDK货导策略10；
   * <p>
   * 12，无路况+不走高速：基于历史的通行速度数据，不考虑当前路况的影响，且不走高速路线，返回速度优先的路。
   */
  private Integer strategy = 11;

  /**
   * 高德此分类依据国标。
   * <p>
   * 1：微型车，
   * <p>
   * 2：轻型车，
   * <p>
   * 3：中型车，
   * <p>
   * 4：重型车
   */
  private Integer size = 3;

  /**
   * 当取值为0时，steps字段内容正常返回；当取值为1时，steps字段内容为空。
   */
  private Integer nosteps = 0;
  //

  @Override
  public String toString() {
    return JacksonUtil.toString(this);
  }

}
