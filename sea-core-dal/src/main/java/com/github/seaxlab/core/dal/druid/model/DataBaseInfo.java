package com.github.seaxlab.core.dal.druid.model;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
@Data
public class DataBaseInfo {

  private String id;

  private String url;
  private String connectionType;
  private String driver;
  private String username;
  private String password;

  private int initSize;
  private int maxSize;
}
