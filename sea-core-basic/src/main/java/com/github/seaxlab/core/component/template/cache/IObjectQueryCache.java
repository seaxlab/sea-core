package com.github.seaxlab.core.component.template.cache;

import java.util.Optional;

/**
 * i query cache1
 *
 * @author spy
 * @version 1.0 2023/09/14
 * @since 1.0
 */
public interface IObjectQueryCache<V> {

  /**
   * 从缓存中获取单个数据
   *
   * @return value
   */
  Optional<V> get();

}
