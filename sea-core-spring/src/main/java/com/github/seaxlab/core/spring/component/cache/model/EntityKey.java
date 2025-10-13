package com.github.seaxlab.core.spring.component.cache.model;

/**
 * 返回实体唯一key
 *
 * @author spy
 * @version 1.0 2021/9/24
 * @since 1.0
 */
public interface EntityKey<T> {

  T getEntityKey();
}
