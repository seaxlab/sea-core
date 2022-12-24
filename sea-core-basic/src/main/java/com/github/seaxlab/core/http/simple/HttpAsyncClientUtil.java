package com.github.seaxlab.core.http.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.Future;

/**
 * 同步非阻塞 http
 *
 * @author spy
 * @version 1.0 2019-07-24
 * @since 1.0
 */
@Slf4j
public class HttpAsyncClientUtil {

  public static CloseableHttpAsyncClient httpAsyncClient = null;

  /**
   * 连接超时时间10秒
   */
  public static int connectionTimeout = 10000;

  /**
   * 请求连接超时时间5秒
   */
  public static int connectionRequestTimeout = 5000;

  /**
   * 最大并发数
   */
  public static int managerMaxTotal = 300;

  /**
   * 3秒sokect无反应强制断开
   */
  public static int socketTimeOut = 3000;

  public static String DEFAULT_CHARSET = "utf-8";

  /**
   * 绕过验证
   *
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sc = SSLContext.getInstance("SSLv3");

    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
    X509TrustManager trustManager = new X509TrustManager() {
      @Override
      public void checkClientTrusted(
        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
        String paramString) throws CertificateException {
      }

      @Override
      public void checkServerTrusted(
        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
        String paramString) throws CertificateException {
      }

      @Override
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    };
    sc.init(null, new TrustManager[]{trustManager}, null);
    return sc;
  }

  public static HttpAsyncClient init() {
    if (httpAsyncClient != null) {
      return httpAsyncClient;
    }

    //绕过证书验证，处理https请求
    SSLContext sslcontext = null;
    try {
      sslcontext = createIgnoreVerifySSL();


      // 设置协议http和https对应的处理socket链接工厂的对象
      Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy>create()
                                                                                 .register("http", NoopIOSessionStrategy.INSTANCE)
                                                                                 .register("https", new SSLIOSessionStrategy(sslcontext))
                                                                                 .build();
      //配置io线程
      IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                                                       .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                                                       .build();
      //设置连接池大小
      ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
      PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry);
      connManager.setMaxTotal(managerMaxTotal);


      httpAsyncClient = HttpAsyncClients.custom()
                                        .setConnectionManager(connManager)
                                        .build();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return httpAsyncClient;
  }


  public static String get(final String url) {

    init();

    RequestConfig requestConfig = RequestConfig.custom()
                                               .setConnectionRequestTimeout(connectionRequestTimeout)
                                               .setConnectTimeout(connectionTimeout)
                                               .build();

    HttpGet request = new HttpGet(url);
    request.setConfig(requestConfig);
    request.setHeader("User-Agent", "Chrome/sea-core");

    httpAsyncClient.start();

    String result = null;

    try {

      Future<HttpResponse> future = httpAsyncClient.execute(request, null);
      HttpResponse response = future.get();


      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
      } else {
        log.warn("http status not 200, status code={}", response.getStatusLine().getStatusCode());
      }


    } catch (Exception e) {
      log.error("http get error", e);
    }

    return result;
  }


}
