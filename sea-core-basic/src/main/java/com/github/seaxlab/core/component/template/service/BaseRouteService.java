package com.github.seaxlab.core.component.template.service;

import lombok.extern.slf4j.Slf4j;

/**
 * base route service
 *
 * @author spy
 * @version 1.0 2023/4/18
 * @since 1.0
 */
@Slf4j
public class BaseRouteService<I, R> implements RouteService<I, R> {
  @Override
  public R route(I input) {
    return null;
  }
}
