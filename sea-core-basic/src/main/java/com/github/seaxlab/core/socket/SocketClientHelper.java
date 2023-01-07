package com.github.seaxlab.core.socket;

import com.github.seaxlab.core.socket.model.SocketClientConfig;
import com.github.seaxlab.core.socket.pool.SocketClientFactory;
import com.github.seaxlab.core.socket.pool.SocketClientPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/6
 * @since 1.0
 */
@Slf4j
public class SocketClientHelper {

  private final static ConcurrentHashMap<String, SocketClientHelper> socketPools = new ConcurrentHashMap<>();


  private final SocketClientPool socketClientPool;

  private SocketClientHelper(SocketClientPool socketClientPool) {
    this.socketClientPool = socketClientPool;
  }

  /**
   * @param host Host del socket a conectarse
   * @param port Puerto del socket a conectarse
   * @return Instancia unica del helper
   */
  public static SocketClientHelper getInstance(String host, int port) {
    String key = host + ":" + port;
    if (!socketPools.containsKey(key)) {
      synchronized (SocketClientHelper.class) {
        SocketClientConfig socketClientConfig = new SocketClientConfig();
        socketClientConfig.setHost(host);
        socketClientConfig.setPort(port);
        SocketClientFactory factory = new SocketClientFactory(socketClientConfig);
        GenericObjectPoolConfig config = getDefaultConfig();
        SocketClientPool newPool = new SocketClientPool(factory, config);
        try {
          newPool.preparePool();
        } catch (Exception ex) {
          log.error("fail to create pool.", ex);
        }
        SocketClientHelper newInstance = new SocketClientHelper(newPool);
        socketPools.put(key, newInstance);
      }
    }
    return socketPools.get(key);
  }

  /**
   * shutdown.
   */
  public void shutdown() {
    SocketClientFactory factory = (SocketClientFactory) socketClientPool.getFactory();
    String key = factory.getHost() + ":" + factory.getPort();
    socketPools.remove(key);
  }

  /**
   * default pool config.
   *
   * @return GenericObjectPoolConfig
   */
  private static GenericObjectPoolConfig getDefaultConfig() {
    GenericObjectPoolConfig defaultConfig = new GenericObjectPoolConfig();
    defaultConfig.setMaxTotal(200);

    return defaultConfig;
  }


  /**
   * get socket client.
   */
  public SocketClient getSocket() {
    try {
      return this.socketClientPool.borrowObject();
    } catch (Exception ex) {
      log.error("get socket error", ex);
      return null;
    }
  }

  /**
   * return socket client.
   *
   * @param socketClient
   */
  public void returnSocket(SocketClient socketClient) {
    this.socketClientPool.returnObject(socketClient);
  }

  /**
   * config
   *
   * @param config
   */
  public void setConfiguration(GenericObjectPoolConfig config) {
    this.socketClientPool.setConfig(config);
    try {
      this.socketClientPool.preparePool();
    } catch (Exception ex) {
      log.error("fail to prepare pool", ex);
    }
  }

  /**
   * Registra en el log el estado actual del pool.
   */
  public void logStatus() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n-------------------------------------").append("\n")
      .append("[max total:").append(this.socketClientPool.getMaxTotal()).append("]")
      .append("[min idle:").append(this.socketClientPool.getMinIdle()).append("]")
      .append("[active count:").append(this.socketClientPool.getNumActive()).append("]")
      .append("[idle count: ").append(this.socketClientPool.getNumIdle()).append("]")
      .append("[total instance: ").append(this.socketClientPool.getNumIdle() + this.socketClientPool.getNumActive()).append("]")
      .append("[waiter count: ").append(this.socketClientPool.getNumWaiters()).append("]")
      .append("\n-------------------------------------").append("\n");
    log.info("socket client pool stats={}", sb.toString());
  }


  /**
   * get feature.
   *
   * @param feature
   * @return Valor
   */
  public int getFeature(StatusPoolFeature feature) {
    switch (feature) {
      case NUM_ACTIVE:
        return this.socketClientPool.getNumActive();
      case NUM_IDLE:
        return this.socketClientPool.getNumIdle();
      case NUM_WAITERS:
        return this.socketClientPool.getNumWaiters();
      case MAX_TOTAL:
        return this.socketClientPool.getMaxTotal();
    }
    return -1;
  }

  public enum StatusPoolFeature {
    NUM_ACTIVE,
    NUM_IDLE,
    NUM_WAITERS,
    MAX_TOTAL;
  }

}
