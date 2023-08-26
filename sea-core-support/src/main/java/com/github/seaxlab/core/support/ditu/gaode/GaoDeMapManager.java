package com.github.seaxlab.core.support.ditu.gaode;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.support.ditu.gaode.dto.DistrictQueryReqDTO;
import com.github.seaxlab.core.support.ditu.gaode.dto.InputTipsReqDTO;
import com.github.seaxlab.core.support.ditu.gaode.dto.ReGeoReqDTO;
import com.github.seaxlab.core.support.ditu.gaode.dto.TruckRoutePlanReqDTO;
import java.util.Map;

/**
 * gao de map manager
 *
 * @author spy
 * @version 1.0 2023/08/26
 * @since 1.0
 */
public class GaoDeMapManager {

  private static final String BASE_URL = "https://restapi.amap.com";


  /**
   * 行政区域查询
   *
   * @param dto
   * @return
   */
  public String queryDistrict(DistrictQueryReqDTO dto) {
    Map<String, Object> params = JacksonUtil.beanToMap(dto);

    return HttpClientUtil.get(BASE_URL + "/v3/config/district", params);
  }

  /**
   * 输入提示（关键字模糊搜索）
   *
   * @param dto
   * @return
   */
  public String queryInputTips(InputTipsReqDTO dto) {
    Map<String, Object> params = JacksonUtil.beanToMap(dto);

    return HttpClientUtil.get(BASE_URL + "/v3/assistant/inputtips", params);
  }

  /**
   * geo->结构化
   *
   * @param dto
   * @return
   */
  public String reGeo(ReGeoReqDTO dto) {
    Map<String, Object> params = JacksonUtil.beanToMap(dto);

    return HttpClientUtil.get(BASE_URL + "/v3/geocode/regeo", params);
  }

  /**
   * 货运导航规划
   *
   * @param dto
   * @return
   */
  public String truckRoutePlan(TruckRoutePlanReqDTO dto) {
    Map<String, Object> params = JacksonUtil.beanToMap(dto);

    return HttpClientUtil.get(BASE_URL + "/v4/direction/truck", params);
  }

}
