package com.github.seaxlab.core.component.template.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * query cache template
 *
 * @author spy
 * @version 1.0 2022/9/10
 * @since 1.0
 */
public interface IQueryCache<K, V> {

  /**
   * 从缓存中获取单个数据
   *
   * @param key
   * @return value
   */
  Optional<V> get(K key);

  /**
   * 多个数据，并转成map
   *
   * @param keys
   * @return
   */
  Map<K, Optional<V>> getMap(Collection<K> keys);

  /**
   * 获取多个列表
   *
   * @param keys
   * @return
   */
  List<V> getList(Collection<K> keys);
}
