package com.github.seaxlab.core.example.service;

import com.github.seaxlab.core.spring.component.mock.annotation.MockMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sea execute service
 *
 * @author spy
 * @version 1.0 2023/10/9
 * @since 1.0
 */
@Service
@Slf4j
public class SeaExecuteService {

  public String add(String a, String b) {
    return a + b;
  }

  public String add(String a, Boolean b) {
    return a + b;
  }

  @MockMethod(key = "mock.user_test_flag", clazz = UserTestService.class, method = "add")
  public String add2(String a, String b) {
    log.info("add2");
    return a + b;
  }

  @MockMethod(key = "mock.user_test_flag", clazz = UserTestService.class)
  public String add3() {
    log.info("add2");
    return "";
  }

}
