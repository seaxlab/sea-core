package com.github.seaxlab.core.component.lock.model;

import java.util.concurrent.locks.Lock;

/**
 * distribute lock key
 *
 * @author spy
 * @version 1.0 2023/01/16
 * @since 1.0
 */
public interface DistributeLockKey {

  Lock get(String key);

}
