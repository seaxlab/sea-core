package com.github.seaxlab.core.loader;

import lombok.extern.slf4j.Slf4j;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
// 注意 这里的LoadLevel
@LoadLevel(name = "UUID2")
@Order(-10)
public class CustomGenerator implements IdGenerator {


  @Override
  public String getId() {

    return "0001";
  }
}
