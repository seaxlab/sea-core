package com.github.seaxlab.core.spring.component.tunnel.util;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * jdk http util
 */
@Slf4j
public final class HttpUtil {

  private HttpUtil() {
  }

  //连接超时时间5s
  private static final int DEFAULT_CONNECT_TIME_OUT = 5000;
  //读超时时间10s
  private static final int DEFAULT_READ_TIME_OUT = 10000;
  private static final String DEFAULT_CHARSET = "UTF-8";

  /**
   * http get
   * @param url http url
   * @param parameters param values
   * @return http result
   */
  public static HttpResult get(String url, Map<String, String> parameters) {
    return get(url, null, parameters);
  }

  /**
   * http get
   *
   * @param url           url
   * @param headers       headers
   * @param parameters   key=value
   * @return http result
   */
  public static HttpResult get(String url, Map<String, String> headers, Map<String, String> parameters) {
    String encodedContent = encodingParams(parameters, DEFAULT_CHARSET);
    url += encodedContent.isEmpty() ? "" : ("?" + encodedContent);

    HttpURLConnection conn = null;
    try {
      conn = getHttpUrlConnection(url);
      conn.setRequestMethod("GET");
      //
      setHeaders(conn, headers);

      conn.connect();
      //构建返回值
      return buildHttpResult(conn);
    } catch (IOException e) {
      log.warn("io exception", e);
      throw new RuntimeException(e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
  }

  /**
   * post 表单
   * @param url http url
   * @param parameters parameters
   * @return http result
   */
  public static HttpResult post(String url, Map<String, String> parameters) {
    return post(url, null, parameters);
  }

  /**
   * post 表单
   *
   * @param url           url
   * @param headers       headers
   * @param parameters   key=value
   * @return the http response of given http post request
   */
  public static HttpResult post(String url, Map<String, String> headers, Map<String, String> parameters) {
    String encodedContent = encodingParams(parameters, StandardCharsets.UTF_8.toString());

    HttpURLConnection conn = null;
    try {
      conn = getHttpUrlConnection(url);
      conn.setRequestMethod("POST");
      conn.setDoOutput(true); //允许输入流，即允许下载
      conn.setDoInput(true); //允许输出流，即允许上传
      //
      setHeaders(conn, headers);
      //
      //表单
      conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARSET);

      conn.getOutputStream().write(encodedContent.getBytes(StandardCharsets.UTF_8));
      //构建返回值
      return buildHttpResult(conn);
    } catch (IOException e) {
      log.warn("io exception", e);
      throw new RuntimeException(e);
    } finally {
      if (null != conn) {
        conn.disconnect();
      }
    }
  }

  public static HttpResult postJSON(String url, Object payload) {
    return postJSON(url, null, payload);
  }

  /**
   * 发送post json请求
   * @param url http url
   * @param headers headers
   * @param payload payload
   * @return http result
   */
  public static HttpResult postJSON(String url, Map<String, String> headers, Object payload) {
    //
    HttpURLConnection conn = null;
    try {
      conn = getHttpUrlConnection(url);
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setDoInput(true);
      //
      setHeaders(conn, headers);
      //
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Accept", "application/json"); //如果有多个，用逗号隔开
      //构建请求体
      String requestBody = "";
      if (payload instanceof String) {
        requestBody = payload.toString();
      } else {
        requestBody = JSON.toJSONString(payload);
      }
      //构建返回值
      conn.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));
      //
      return buildHttpResult(conn);
    } catch (IOException e) {
      log.warn("io exception", e);
      throw new RuntimeException(e);
    } finally {
      if (null != conn) {
        conn.disconnect();
      }
    }
  }

  /**
   * check url support range
   *
   * @param url request url
   * @return boolean
   */
  public static boolean checkSupportRangeFlag(String url) {

    HttpURLConnection connection = null;
    try {
      connection = getHttpUrlConnection(url);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String acceptRanges = connection.getHeaderField("Accept-Ranges");
    closeHttpUrlConnection(connection);

    if (StringUtils.isEmpty(acceptRanges)) {
      return false;
    }
    return "bytes".equalsIgnoreCase(acceptRanges);
  }

  /**
   * 获取远程文件大小
   *
   * @param netUrl request url
   * @return file content length (byte)
   */
  public static int getFileContentLength(String netUrl) throws Exception {
    HttpURLConnection connection = getHttpUrlConnection(netUrl);
    int contentLength = connection.getContentLength();
    closeHttpUrlConnection(connection);

    return contentLength;
  }

  // -----------------private -------------
  private static String encodingParams(Map<String, String> parameters, String encoding) {
    StringBuilder sb = new StringBuilder();
    if (null == parameters) {
      return "";
    }
    parameters.forEach((key, value) -> {
      try {
        sb.append(key).append("=").append(URLEncoder.encode(value, encoding)).append("&");
      } catch (UnsupportedEncodingException e) {

        throw new RuntimeException(e);
      }
    });
    //
    return StringUtils.removeEnd(sb.toString(), "&");
  }

  private static void setHeaders(HttpURLConnection conn, Map<String, String> headers) {
    if (null != headers) {
      headers.forEach(conn::addRequestProperty);
    }
  }

  /**
   * 获取连接，公共属性设置
   *
   * @param netUrl request url
   */
  private static HttpURLConnection getHttpUrlConnection(String netUrl) throws IOException {
    URL url = new URL(netUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setConnectTimeout(DEFAULT_CONNECT_TIME_OUT); //连接时间
    conn.setReadTimeout(DEFAULT_READ_TIME_OUT);//读取数据时间
    conn.setUseCaches(false); //不使用缓存
    //
    //conn.addRequestProperty("Client-Version", MQVersion.getVersionDesc(MQVersion.CURRENT_VERSION));
    // 防止屏蔽程序抓取而返回403错误
    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    //请求时间
    conn.addRequestProperty("Sea-Core-Client-RequestTS", String.valueOf(System.currentTimeMillis()));
    return conn;
  }

  /**
   * close connection
   *
   * @param connection http connection
   */
  private static void closeHttpUrlConnection(HttpURLConnection connection) {
    if (connection != null) {
      connection.disconnect();
    }
  }

  private static HttpResult buildHttpResult(HttpURLConnection conn) throws IOException {
    int respCode = conn.getResponseCode();
    String resp = null;

    if (HttpURLConnection.HTTP_OK == respCode) {
      resp = IOUtils.toString(conn.getInputStream(), DEFAULT_CHARSET);
    } else {
      resp = IOUtils.toString(conn.getErrorStream(), DEFAULT_CHARSET);
    }


    //优先转json，转换失败默认为string
    Object data;
    try {
      data = JSON.parse(resp);
    } catch (Exception e) {
      log.warn("parse response to json exception", e);
      data = resp;
    }

    return new HttpResult(respCode, data);
  }


  @Data
  public static class HttpResult {
    public int code;
    public Object data;

    public HttpResult(int code, Object data) {
      this.code = code;
      this.data = data;
    }
  }
}
