package com.github.seaxlab.core.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/11/2
 * @since 1.0
 */
@Slf4j
@Component
public class UserTestService {

  public String add(String a, String b) {
    return "mock:" + a + b;
  }


  public String add3() {
    return "mock:";
  }

}
