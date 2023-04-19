package com.github.seaxlab.core.component.template.model;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/19
 * @since 1.0
 */
public interface LockService {

  SeaLock getLock(String lockKey);

}
