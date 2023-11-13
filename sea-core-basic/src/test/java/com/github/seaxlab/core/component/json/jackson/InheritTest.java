package com.github.seaxlab.core.component.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.seaxlab.core.BaseCoreTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/11/13
 * @since 1.0
 */
@Slf4j
public class InheritTest extends BaseCoreTest {

  @Test
  public void test15() throws Exception {
    JsonMapper objectMapper = new JsonMapper();

    log.info("a={}", objectMapper.writeValueAsString(new A()));
    //a={"id":null,"code":null,"name":null}
    log.info("b={}", objectMapper.writeValueAsString(new B()));
    //b={}
  }

  @Data
  public static class A {

    private Long id;
    private String code;
    private String name;
  }


  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(Include.NON_NULL)
  public static class Parent {

  }

  @Data
  public static class B extends Parent {

    private Long id;
    private String code;
    private String name;
  }
}
