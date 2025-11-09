package com.github.seaxlab.core.spring.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.seaxlab.core.spring.component.cache.model.EntityKey;
import lombok.Data;

/**
 * User POJO
 */
@Data
public class User implements EntityKey {

  private String code;
  private String name;

  private int age;

  @Override
  @JSONField(serialize = false, deserialize = false)
  @JsonIgnore
  public String getEntityKey() {
    return code;
  }
}
