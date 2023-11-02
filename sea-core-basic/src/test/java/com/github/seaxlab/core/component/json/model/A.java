package com.github.seaxlab.core.component.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/11/20
 * @since 1.0
 */
@Data
public class A {

  private Long id;
  private String code;
  private String name;
  private transient String password;

  @JsonIgnore
  private final Extend extend = new Extend();

  private final Extend extend2 = new Extend();

  @Data
  public static class Extend {

    private String name;
  }
}
