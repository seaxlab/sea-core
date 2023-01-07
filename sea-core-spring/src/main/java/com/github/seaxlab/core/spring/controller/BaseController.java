package com.github.seaxlab.core.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * controller基类
 *
 * @author spy
 * @version 1.0 2019/3/15
 * @since 1.0
 */
@Slf4j
public class BaseController {


  protected HttpServletRequest getRequest() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
      .currentRequestAttributes())
      .getRequest();

    return request;
  }


}
