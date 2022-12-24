package com.github.seaxlab.core.web.model;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/14
 * @since 1.0
 */
@Data
public class RateLimiterConfig {

  /**
   * support url wildcard pattern
   */
  private String url;

  private int qps;
}
