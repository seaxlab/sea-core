package com.github.seaxlab.core.http.simple;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.http.common.HttpHeaderConst;
import com.github.seaxlab.core.http.dto.HttpUploadDTO;
import com.github.seaxlab.core.http.dto.response.HttpUploadRespDTO;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.JSONUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Http client util
 */
@Slf4j
public class HttpClientUtil {


  public static CloseableHttpClient httpClient = null;

  private static PoolingHttpClientConnectionManager connectionManager;

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
   * 3秒socket无反应强制断开
   */
  public static int socketTimeOut = 3000;

  public static String DEFAULT_CHARSET = "utf-8";

  private HttpClientUtil() {
  }

  public static void init() {
    //初始化httpclient（支持并发）
    if (httpClient == null) {
      SSLContext ctx;//绕过https证书验证
      try {
        ctx = SSLContext.getInstance("TLS");

        X509TrustManager tm = new X509TrustManager() {
          @Override
          public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          }

          @Override
          public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          }

          @Override
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx,
          SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()//
                                                                    .register("http",
                                                                      PlainConnectionSocketFactory.getSocketFactory())//
                                                                    .register("https", ssf) //
                                                                    .build();
        //manager
        connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(managerMaxTotal);
        //socketConfig
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeOut).build();
        httpClient = HttpClients.custom().setConnectionManager(connectionManager) //
                                .setDefaultSocketConfig(socketConfig) //
                                .build();
      } catch (Exception ex) {
        log.error("httpClient——初始化——报错，错误信息", ex);
      }
    }
  }

  public static PoolingHttpClientConnectionManager getConnectionManager() {
    return connectionManager;
  }

  /**
   * get http client.
   *
   * @return CloseableHttpClient
   */
  public static CloseableHttpClient getHttpClient() {
    init();
    return httpClient;
  }

  /**
   * 发送post请求
   *
   * @param url     url
   * @param map     map
   * @param charset charset
   * @return string
   */
  public static String post(String url, Map<String, Object> map, String charset) {
    init();//初始化检查

    HttpPost httpPost = new HttpPost(url);

    List<NameValuePair> list = new ArrayList<>();
    for (Entry<String, Object> elem : map.entrySet()) {
      list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
    }
    String result = null;

    try {
      if (!list.isEmpty()) {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
        httpPost.setEntity(entity);
      }
      //requestConfig
      RequestConfig requestConfig = RequestConfig.custom() //
                                                 .setConnectionRequestTimeout(connectionRequestTimeout) //
                                                 .setConnectTimeout(connectionTimeout) //
                                                 .build();
      httpPost.setConfig(requestConfig);
      try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
        result = getRespEntityStr(response);
      }
    } catch (Exception e) {
      log.error("http exception", e);
      ExceptionHandler.publish(ErrorMessageEnum.HTTP_ERROR);
    }
    return result;
  }

  /**
   * 发送POST请求
   *
   * @param url url
   * @param map map
   * @return String
   */
  public static String post(String url, Map<String, Object> map) {
    return post(url, map, DEFAULT_CHARSET);
  }


  /**
   * 发送POST, application/json请求
   *
   * @param url url
   * @return String
   */
  public static String postJSON(final String url) {
    return postJSON(url, null);
  }

  /**
   * send post request.
   *
   * @param url     remote url
   * @param payload payload.
   * @return string
   */
  public static String postJSON(String url, Object payload) {
    return postJSON(url, payload, null);
  }

  /**
   * 发送POST, application/json请求
   *
   * @param url     remote url
   * @param payload payload
   * @param headers http headers
   * @return string
   */
  public static String postJSON(String url, Object payload, Map<String, String> headers) {
    init();//初始化检查
    HttpPost httpPost = new HttpPost(url);

    httpPost.addHeader("Content-Type", "application/json;charset=utf8");

    if (headers != null && !headers.isEmpty()) {
      headers.forEach(httpPost::addHeader);
    }

    log.info("request url={}", url);

    if (payload != null) {
      String jsonStr;
      if (payload instanceof String) {
        jsonStr = (String) payload;
      } else {
        jsonStr = JSONUtil.toStr(payload);
      }
      StringEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
      httpPost.setEntity(entity);
    }
    //requestConfig
    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                                               .setConnectTimeout(connectionTimeout).build();
    httpPost.setConfig(requestConfig);

    String result = null;
    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
      result = getRespEntityStr(response);
    } catch (Exception e) {
      log.error("http exception", e);
      ExceptionHandler.publish(ErrorMessageEnum.HTTP_ERROR);
    }
    return result;
  }

  /**
   * post with no exception
   *
   * @param url url
   * @param obj obj
   * @return result
   */
  public static Result<String> postJSONSafe(String url, Object obj) {
    Result<String> result = Result.fail();

    try {
      String response = postJSON(url, obj);
      result.setData(response);
      result.setSuccess(true);
    } catch (Exception e) {
      log.error("http exception", e);
      result.setMsg(e.getMessage());
    }

    return result;
  }


  public static Result<String> getSafe(final String url, Map<String, Object> param) {
    Result<String> result = Result.fail();

    try {
      String content = get(url, param);
      result.setData(content);
      result.setSuccess(true);
    } catch (Exception e) {
      result.setMsg(e.getMessage());
    }

    return result;
  }

  /**
   * 发送GET请求
   *
   * @param url   url
   * @param param param
   * @return string
   */
  public static String get(final String url, Map<String, Object> param) {

    List<NameValuePair> list = new ArrayList<>();
    for (Entry<String, Object> elem : param.entrySet()) {
      list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
    }

    String queryStr = URLEncodedUtils.format(list, DEFAULT_CHARSET);

    String finalUrl = url + (url.indexOf('?') > 0 ? "&" : "?") + queryStr;

    return get(finalUrl);
  }

  public static Result<String> getSafe(final String url) {
    Result<String> result = Result.fail();

    try {
      String content = get(url);
      result.setData(content);
      result.setSuccess(true);
    } catch (Exception e) {
      result.setMsg(e.getMessage());
    }

    return result;
  }

  /**
   * 发送GET请求
   *
   * @param url url
   * @return string
   */
  public static String get(final String url) {
    log.info("http get method, url=[{}]", url);
    init();

    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                                               .setConnectTimeout(connectionTimeout).build();

    HttpGet request = new HttpGet(url);
    request.setConfig(requestConfig);
    request.setHeader("User-Agent", "Chrome/Sea");

    Stopwatch stopwatch = Stopwatch.createStarted();

    String result = null;
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      result = getRespEntityStr(response);
    } catch (Exception e) {
      log.error("http exception", e);
      ExceptionHandler.publish(ErrorMessageEnum.HTTP_ERROR);
    } finally {
      stopwatch.stop();
      log.info("http get, cost={}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    return result;
  }

  /**
   * 获取远端文件大小
   *
   * @param url url
   * @return long
   */
  public static long getContentLength(String url) {
    long contentLength = 0;
    HttpHead httpHead = new HttpHead(url);

    try (CloseableHttpResponse response = getHttpClient().execute(httpHead)) {

      if (!response.containsHeader(HttpHeaderConst.CONTENT_LENGTH)) {
        log.warn("Content-Length is not exist");
        return contentLength;
      }

      Header header = response.getLastHeader(HttpHeaderConst.CONTENT_LENGTH);

      contentLength = StringUtil.isEmpty(header.getValue()) ? 0 : Long.parseLong(header.getValue());
    } catch (Exception e) {
      log.error("http exception.", e);
    }
    log.info("remote file size={}", contentLength);
    return contentLength;
  }

  /**
   * upload file to remote server.
   *
   * @param dto request
   * @return result
   */
  public static Result<HttpUploadRespDTO> upload(HttpUploadDTO dto) {
    Result<HttpUploadRespDTO> result = Result.fail();

    //Creating the MultipartEntityBuilder
    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

    //Setting the mode
    entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

    //Adding text
    Map<String, Object> textFieldMap = dto.getTextFieldMap();
    if (textFieldMap != null && !textFieldMap.isEmpty()) {
      textFieldMap.forEach((key, value) -> {
        if (value != null) {
          entityBuilder.addTextBody(key, value.toString());
        }
      });
    }

    //Adding a file
    Map<String, File> fileFieldMap = dto.getFileFieldMap();
    if (fileFieldMap != null && !fileFieldMap.isEmpty()) {
      fileFieldMap.forEach((key, value) -> {
        if (value != null) {
          entityBuilder.addBinaryBody(key, value);
        }
      });
    }

    // add stream support.
    Map<String, InputStream> streamFieldMap = dto.getStreamFieldMap();
    if (streamFieldMap != null && !streamFieldMap.isEmpty()) {
      streamFieldMap.forEach((key, value) -> {
        if (value != null) {
          entityBuilder.addBinaryBody(key, value);
        }
      });
    }

    //Building a single entity using the parts
    HttpEntity httpEntity = entityBuilder.build();

    //Building the RequestBuilder request object
    RequestBuilder requestBuilder = RequestBuilder.post(dto.getUrl());

    //Set the entity object to the RequestBuilder
    requestBuilder.setEntity(httpEntity);

    //Building the request
    HttpUriRequest multipartRequest = requestBuilder.build();

    //Executing the request
    try (CloseableHttpResponse response = getHttpClient().execute(multipartRequest)) {

      String respStr = getRespEntityStr(response);
      //
      HttpUploadRespDTO respDTO = new HttpUploadRespDTO();
      respDTO.setContent(respStr);
      result.value(respDTO);
    } catch (Exception e) {
      result.setMsg(e.getMessage());
    }

    return result;
  }

  //-------- private method -------

  /**
   * get response entity.
   *
   * @param response response
   * @return string
   * @throws IOException io exception
   */
  private static String getRespEntityStr(CloseableHttpResponse response) throws IOException {
    String result = "";
    if (response != null) {
      StatusLine statusLine = response.getStatusLine();
      if (statusLine == null) {
        log.warn("status line is null");
      } else {
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
          result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        } else {
          log.warn("http status not 200, status code={}", response.getStatusLine().getStatusCode());
        }
      }
    }
    return result;
  }
}
