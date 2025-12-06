package com.github.seaxlab.core.component.socket.pool;

import com.github.seaxlab.core.component.socket.SocketClient;
import com.github.seaxlab.core.component.socket.impl.DefaultSocketClient;
import com.github.seaxlab.core.component.socket.model.SocketClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/5
 * @since 1.0
 */
@Slf4j
public class SocketClientFactory implements PooledObjectFactory<SocketClient> {

  private final SocketClientConfig socketClientConfig;

  private final String host;
  private final int port;

  public SocketClientFactory(SocketClientConfig socketClientConfig) {
    this.socketClientConfig = socketClientConfig;
    this.host = socketClientConfig.getHost();
    this.port = socketClientConfig.getPort();
  }

  private SocketClient create() throws Exception {
    SocketClient socket = new DefaultSocketClient();
    socket.init(socketClientConfig);
    return socket;
  }

  private PooledObject<SocketClient> wrap(SocketClient t) {
    return new DefaultPooledObject<>(t);
  }

  @Override
  public PooledObject<SocketClient> makeObject() throws Exception {
    return this.wrap(this.create());
  }

  @Override
  public void destroyObject(PooledObject<SocketClient> p) throws Exception {
    p.getObject().close();
  }

  @Override
  public boolean validateObject(PooledObject<SocketClient> p) {
    return p.getObject().isValid();
  }

  @Override
  public void activateObject(PooledObject<SocketClient> p) throws Exception {
    p.getObject().activate();
  }

  @Override
  public void passivateObject(PooledObject<SocketClient> p) throws Exception {
    p.getObject().inActivate();
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }
}
