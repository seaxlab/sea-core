package com.github.seaxlab.core.socket.pool;

import com.github.seaxlab.core.socket.SocketClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/5
 * @since 1.0
 */
@Slf4j
public class SocketClientPool extends GenericObjectPool<SocketClient> {
  public SocketClientPool(PooledObjectFactory<SocketClient> factory) {
    super(factory);
  }

  public SocketClientPool(PooledObjectFactory<SocketClient> factory, GenericObjectPoolConfig<SocketClient> config) {
    super(factory, config);
  }
}
