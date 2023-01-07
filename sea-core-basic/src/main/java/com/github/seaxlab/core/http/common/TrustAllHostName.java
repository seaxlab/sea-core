package com.github.seaxlab.core.http.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
public enum TrustAllHostName implements HostnameVerifier {
  /**
   * 实例
   */
  INSTANCE;

  @Override
  public boolean verify(String s, SSLSession sslSession) {
    return true;
  }
}