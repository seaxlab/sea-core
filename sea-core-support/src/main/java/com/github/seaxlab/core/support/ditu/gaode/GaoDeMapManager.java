package com.github.seaxlab.core.support.ditu.gaode;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
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

  public String truckRoutePlan(TruckRoutePlanReqDTO dto) {
    Map<String, Object> params = JacksonUtil.beanToMap(dto);

    return HttpClientUtil.get(BASE_URL + "/v4/direction/truck", params);
  }

}
