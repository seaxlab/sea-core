package com.github.seaxlab.core.spring.component.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * Json request wrapper
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class JsonRequestWrapper extends HttpServletRequestWrapper {

  private final ParameterMap<String, String[]> parameters;

  private final String body;
  private JSON json = null;
  private boolean parsed = false;

  /**
   * Constructs a request object wrapping the given request.
   *
   * @param request the {@link HttpServletRequest} to be wrapped.
   * @throws IllegalArgumentException if the request is null
   */
  public JsonRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
      char[] charBuffer = new char[128];
      int bytesRead;
      while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
        stringBuilder.append(charBuffer, 0, bytesRead);
      }
    }
    body = stringBuilder.toString();
    Map<String, String[]> map = super.getParameterMap();
    if (map instanceof ParameterMap) {
      this.parameters = (ParameterMap<String, String[]>) map;
    } else {
      this.parameters = new ParameterMap<>(map);
    }
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return new ServletInputStream() {

      private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
      private boolean finished = false;

      @Override
      public boolean isFinished() {
        return this.finished;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
      }

      @Override
      public int read() throws IOException {
        int data = byteArrayInputStream.read();
        this.finished = data == -1;
        return data;
      }
    };
  }


  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }

  @Override
  public String getParameter(String name) {
    String[] value = parameters.get(name);
    if (value == null || value.length == 0) {
      return null;
    }
    return value[0];
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return parameters;
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return Collections.enumeration(parameters.keySet());
  }

  @Override
  public String[] getParameterValues(String name) {
    return parameters.get(name);
  }

  /**
   * 设置参数
   *
   * @param name
   * @param values
   */
  public synchronized void setParameter(String name, String... values) {
    try {
      parameters.setLocked(false);
      parameters.put(name, values);
    } finally {
      parameters.setLocked(true);
    }
  }

  /**
   * 清空参数
   */
  public synchronized void clearParameters() {
    try {
      parameters.setLocked(false);
      parameters.clear();
    } finally {
      parameters.setLocked(true);
    }
  }

  /**
   * 获取请求体
   *
   * @return
   */
  public String getRequestBody() {
    return this.body;
  }

  public JSON getJson() {
    if (json != null) {
      return json;
    }
    try {
      if ((this.body.startsWith("{") && this.body.endsWith("}")) || (this.body.startsWith("[") && this.body.endsWith("]"))) {
        return json = (JSON) JSON.parse(this.body);
      }
    } catch (Exception e) {
      log.error("parse request body exception", e);
    }
    return null;
  }

  public JSONObject getJsonObject() {
    parseJSON();
    if (json instanceof JSONObject) {
      return (JSONObject) json;
    }
    return new JSONObject();
  }

  public JSONArray getJsonArray() {
    parseJSON();
    if (json instanceof JSONArray) {
      return (JSONArray) json;
    }
    return new JSONArray();
  }

  //
  private synchronized void parseJSON() {
    if (parsed) {
      return;
    }
    try {
      if ((this.body.startsWith("{") && this.body.endsWith("}"))) {
        json = JSONUtil.toJSONObj(this.body);
      } else if ((this.body.startsWith("[") && this.body.endsWith("]"))) {
        json = JSONUtil.toJSONArray(this.body);
      }
    } catch (Exception e) {
      log.error("parse request body exception", e);
    } finally {
      parsed = true;
    }
  }

}


