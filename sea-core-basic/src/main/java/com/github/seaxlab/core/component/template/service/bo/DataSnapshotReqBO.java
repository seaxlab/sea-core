package com.github.seaxlab.core.component.template.service.bo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * snapshot Request BO
 *
 * @author spy
 * @version 1.0 2023/04/28
 * @since 1.0
 */
@Data
public class DataSnapshotReqBO {

  private final Map<String, Object> params = new HashMap<>();

  private final Extend extend = new Extend();


  public void putParam(String key, Object value) {
    params.put(key, value);
  }

  public void removeParam(String key) {
    params.remove(key);
  }


  @Data
  public static class Extend {

    private Collection<?> records;
  }
}
