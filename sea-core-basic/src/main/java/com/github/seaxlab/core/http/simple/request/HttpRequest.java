package com.github.seaxlab.core.http.simple.request;

import lombok.Builder;
import lombok.Data;
import org.apache.http.HttpResponse;

import java.util.Map;
import java.util.function.Consumer;

/**
 * http request
 *
 * @author spy
 * @version 1.0 2026/2/6
 * @since 1.0
 */
@Data
@Builder
public class HttpRequest {
  /**
   * url
   */
  private String url;
  /**
   * POST中payload
   */
  private Object payload;
  /**
   * headers
   */
  private Map<String, String> headers;
  /**
   * response响应处理
   */
  private Consumer<HttpResponse> responseConsumer;
}
