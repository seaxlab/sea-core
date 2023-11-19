package com.github.seaxlab.core.spring.component.cache;

import com.github.seaxlab.core.enums.IBaseEnum;
import lombok.Getter;

/**
 * cache op enum
 *
 * @author spy
 * @version 1.0 2021/6/8
 * @since 1.0
 */
@Getter
public enum CacheOpEnum implements IBaseEnum<String> {
  SET("set", "set op"),
  SET_MAP("set_map", "set map op"),

  GET("get", "get op"),
  GET_MAP("get_map", "get map op"),

  DELETE("delete", "delete op"),
  SCAN("scan", "scan op")

  // add here
  ;

  private final String code;
  private final String desc;

  CacheOpEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
