package com.github.seaxlab.core.spring.component.tunnel.bo;

import lombok.Data;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/3/21
 * @since 1.0
 */
@Data
public class HttpTunnelReqBO {

  private String url;
  private String method;// GET/POST/POST_JSON
  //
  private Map<String, String> headers;
  //
  private Map<String, String> parameters; // just for get/post
  private String requestBody; // for post json
  //TODO
  private String returnValueType;//JSON/html

}
