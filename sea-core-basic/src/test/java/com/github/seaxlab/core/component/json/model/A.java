package com.github.seaxlab.core.component.json.model;

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
  private String name;
  private transient String password;
}
