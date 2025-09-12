package com.github.seaxlab.core.spring.component.tunnel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.spring.component.tunnel.bo.HttpTunnelReqBO;
import com.github.seaxlab.core.spring.component.tunnel.service.HttpTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * default http tunnel service
 *
 * @author spy
 * @version 1.0 2025/3/21
 * @since 1.0
 */
@Slf4j
public class DefaultHttpTunnelService implements HttpTunnelService {


  @Override
  public Object execute(HttpTunnelReqBO bo) {
    log.info("try to execute, request={}", JSON.toJSONString(bo));
    //
    Object value = null;
    if ("GET".equalsIgnoreCase(bo.getMethod())) {
      //
      value = HttpUtil.get(bo.getUrl(), bo.getHeaders(), bo.getParameters());
//        value = JSON.toJSONString(value);
    } else if ("POST".equalsIgnoreCase(bo.getMethod())) {
      //
      value = HttpUtil.post(bo.getUrl(), bo.getHeaders(), bo.getParameters());
    } else if ("POST_JSON".equalsIgnoreCase(bo.getMethod())) {
      //
      if (StringUtils.isNotBlank(bo.getRequestBody())) { //优先使用requestBody
        value = HttpUtil.postJSON(bo.getUrl(), bo.getHeaders(), bo.getRequestBody());
      } else { //降级使用parameters
        value = HttpUtil.postJSON(bo.getUrl(), bo.getHeaders(), bo.getParameters());
      }
    } else {
      value = "method is not supported," + bo.getMethod();
    }
    return value;
  }

}
