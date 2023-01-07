package com.github.seaxlab.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.model.Result;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/17
 * @since 1.0
 */
@Slf4j
public final class WebServiceUtil {

  private static JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

  private static Map<String, Client> clientMap = Maps.newConcurrentMap();

  /**
   * generate cxf client.
   *
   * @param uri
   * @return
   */
  public static Client getClient(String uri) {
    //Client client = clientMap.get(uri);
    //if (client == null) {
    //    client = dcf.createClient(uri);
    //    clientMap.put(uri, client);
    //}
    //return client;

    clientMap.computeIfAbsent(uri, key -> dcf.createClient(uri));
    return clientMap.get(uri);
  }

  /**
   * 发送http post请求
   *
   * @param uri
   * @param method
   * @return
   */
  public static Result<String> post(String uri, String method, Object[] params) {
    String flowId = IdUtil.shortUUID();
    log.info("请求：request[{}] url={},method={},params={},", flowId, uri, method, JSON.toJSONString(params));
    Result<String> result = Result.fail();
    try {
      Client client = getClient(uri);
      Object[] response = client.invoke(method, params);
      result.value(response[0].toString());
      if (log.isInfoEnabled()) {
        log.info("响应：response[{}] method={},result={}", flowId, method, JSONObject.toJSONString(response[0].toString()));
      }
    } catch (Exception e) {
      log.error("请求异常:error[{}] method={}, request webService invoke error", flowId, method, e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * 调用webservice
   *
   * @param uri
   * @param method
   * @param params
   * @return 返回所有响应数据，所有数据已转成字符串
   */
  public static Result<Object> invoke(String uri, String method, Object[] params) {
    String flowId = IdUtil.shortUUID();
    log.error("request[{}] params : {},method:{}", flowId, JSON.toJSONString(params), method);
    Result<Object> result = Result.fail();
    try {
      Client client = getClient(uri);
      Object[] response = client.invoke(method, params);
      String[] responseStringArray = new String[response.length];
      for (int i = 0; i < response.length; i++) {
        responseStringArray[i] = response[i].toString();
      }
      result.value(responseStringArray);
      log.error("请求：request[{}],method={}, response={}", flowId, method, Arrays.toString(response));
    } catch (Exception e) {
      log.error("请求异常:error[{}] method={}, request webService error", flowId, method, e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * 发送http post请求
   *
   * @param uri
   * @param method
   * @return
   */
  public static Result<String> post(String uri, String method, String requestParams) {
    Result<String> result = Result.fail();
    try {
      Object[] response = postBase(uri, method, null, requestParams, 0);
      result.value(response[0].toString());
    } catch (Exception e) {
      log.error("请求webservice异常", e);
      result.setMsg(e.getMessage());
    }
    return result;
  }


  /**
   * 发送http post请求
   * 双参
   *
   * @param uri
   * @param method
   * @return
   */
  public static Result<String> post(String uri, String method, String paramMethod, String requestParams) {
    Result<String> result = Result.fail();
    try {
      log.info("请求:method={},paramMethod={},requestParams={}", method, paramMethod, requestParams);

      Client client = getClient(uri);
      Object[] response = client.invoke(method, paramMethod, requestParams);
      result.value(response[0].toString());
      log.info("响应：{}, response={}", method, response[0].toString());
    } catch (Exception e) {
      log.error("请求异常：method={}, client error={}", method, e);
      result.setMsg(e.getMessage());
    }
    return result;
  }


  /**
   * 调用webservice
   *
   * @param uri
   * @param method
   * @param params
   * @return 返回所有响应数据，所有数据已转成字符串
   */
  public static Result<Object> invoke(String uri, String method, Object params) {
    String flowId = IdUtil.shortUUID();
    log.error("request[{}] params:{},method:{}", flowId, JSON.toJSONString(params), method);
    Result<Object> result = Result.fail();
    try {
      Client client = getClient(uri);
      Object[] response = client.invoke(method, params);

      result.value(response[0]);
      log.error("请求：request={}, {}, response=", flowId, method, ToStringBuilder.reflectionToString(response[0]));
    } catch (Exception e) {
      log.error("request[{}], error={}", flowId, e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * 发送http post请求
   * 日志不输出响应内容，应对物价查询响应过大
   *
   * @param uri
   * @param method
   * @return
   */
  public static Result<String> postNoLog(String uri, String method, Object[] params) {
    String flowId = IdUtil.shortUUID();
    log.error("request[{}] params : {},method:{}", flowId, JSON.toJSONString(params), method);
    Result<String> result = Result.fail();
    try {
      Client client = getClient(uri);
      Object[] response = client.invoke(method, params);
      result.value(response[0].toString());
    } catch (Exception e) {
      log.error("fail to webservice invoke, request[{}],error {}", flowId, e);
      result.setMsg(e.getMessage());
    }
    return result;
  }

  /**
   * 自定义namespace  POST请求
   *
   * @param url
   * @param method
   * @param namespace     自定义namespace
   * @param requestParams
   * @return
   */
  public static Result<String> postForJsonNamespace(String url, String method, String namespace, Object requestParams) {
    Result<String> result = Result.fail();

    try {
      Object[] response = postBase(url, method, namespace, requestParams, 0);
      if (response == null) {
        result.setMsg("返回数据为空");
        return result;
      }
      result.value(JSON.toJSONString(response[0]));
    } catch (Exception e) {
      log.error("fail to post for json namespace", e);
      result.setMsg(e.getMessage());
    }

    return result;
  }

  /**
   * 自定义namespace  POST请求   对应返回得xml格式数据
   *
   * @param url
   * @param method
   * @param namespace
   * @param requestParams
   * @return
   */
  public static Result<String> postForXmlNamespace(String url, String method, String namespace, Object requestParams) {
    Result<String> result = Result.fail();

    try {
      Object[] response = postBase(url, method, namespace, requestParams, 0);
      result.value(response[0].toString());
    } catch (Exception e) {
      log.error("fail to post for xml namespace", e);
      result.setMsg(e.getMessage());
    }

    return result;
  }

  /**
   * 通用请求
   *
   * @param uri         请求地址
   * @param method      方法
   * @param nameSpace   命名空间
   * @param param       参数
   * @param resultIndex 结果下标
   * @return
   */
  public static Object[] postBase(String uri, String method, String nameSpace, Object param, Integer resultIndex) throws Exception {

    Object[] responseArray = null;
    //sofaTracer节点记录
    //SimpleTracer simpleTracer = SimpleTracer.getTracerSingleton();
    //SofaTracerSpan tracerSpan = null;
    //String traceResultCode = SimpleTracer.RESULT_CODE_FAIL;

    //Stopwatch stopwatch = Stopwatch.createStarted();
    //Map<String, String> tags = new HashMap<>();
    //tags.put("external-method", method);

    try {
      Client client = getClient(uri);
      if (StringUtil.isBlank(nameSpace)) {
        nameSpace = client.getEndpoint().getService().getName().getNamespaceURI();
      }
      QName qName = new QName(nameSpace, method);
      //tracerSpan = simpleTracer.begin("external " + method);
      //tracerSpan.setTag("external.url", uri);
      if (param instanceof Object[]) {
        responseArray = client.invoke(qName, (Object[]) param);
      } else {
        responseArray = client.invoke(qName, param);
      }

      if (resultIndex != null) {
        log.info("webservice response, method: {} , result: {}", method,
          responseArray == null || responseArray[resultIndex] == null ? new Object[]{null} :
            JSONObject.toJSONString(responseArray[resultIndex]));
      } else {
        log.info("webservice response, method: {}, result: {}", method, responseArray == null ? new Object[]{null} : JSONObject.toJSONString(responseArray));
      }

    } catch (Exception e) {
      log.error("webservice request error: ", e);
      throw e;
    } finally {
      //long cost = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      //simpleTracer.end(traceResultCode);
    }

    return responseArray;
  }


}
