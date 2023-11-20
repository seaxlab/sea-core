package com.github.seaxlab.core.component.socket;

import com.github.seaxlab.core.component.socket.model.SocketClientConfig;
import com.github.seaxlab.core.component.socket.model.SocketClientSendData;

/**
 * socket client.
 *
 * @author spy
 * @version 1.0 2021/1/5
 * @since 1.0
 */
public interface SocketClient {

  /**
   * init
   *
   * @param clientConfig
   */
  void init(SocketClientConfig clientConfig);

  /**
   * send data
   *
   * @param data
   * @return
   */
  byte[] send(SocketClientSendData data);

  /**
   * close socket client.
   */
  void close();

  boolean isValid();

  void activate();

  void inActivate();
}
