package com.github.seaxlab.core.spring.web.util;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.common.WebConst;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * request util in spring mvc
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public final class RequestUtil {

  private RequestUtil() {
  }

  /**
   * get request in spring mvc
   *
   * @return request
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (servletRequestAttributes == null) {
      ExceptionHandler.publishMsg("非Web上下文无法获取Request");
    }
    return servletRequestAttributes.getRequest();
  }

  /**
   * get param
   * priority HEADER> PARAMETER > COOKIE
   *
   * @param request http request
   * @param key     key
   * @return
   */
  public static String getParamByPriority(ServerHttpRequest request, String key) {
    Preconditions.checkNotNull(key, "key不能为空");

    return getParamByPriority(request, key, key, key);
  }

  /**
   * get param by priority
   * header -> request -> cookie
   *
   * @param request    http request
   * @param headerKey  header key
   * @param requestKey request key
   * @param cookieKey  cookie key.
   * @return
   */
  public static String getParamByPriority(ServerHttpRequest request, String headerKey, String requestKey, String cookieKey) {
    if (StringUtil.isAllEmpty(headerKey, requestKey, cookieKey)) {
      return StringUtil.EMPTY;
    }

    // header
    String value = request.getHeaders().getFirst(headerKey);
    if (StringUtil.isEmpty(value)) {
      // request
      value = request.getQueryParams().getFirst(requestKey);
      if (StringUtil.isEmpty(value)) {
        // cookie
        HttpCookie cookie = request.getCookies().getFirst(cookieKey);
        value = cookie == null ? StringUtil.EMPTY : cookie.getValue();
      }
    }
    return value;
  }


  /**
   * print simple request info
   *
   * @param request
   */
  public static void logSimple(ServerHttpRequest request) {
    log.info("sea request log: [{},{}] id={},queryString={},userAgent={},ip={}", request.getMethodValue(), request.getPath(), request.getId(), request.getQueryParams(), getUserAgent(request), getClientIpAddress(request));
  }

  /**
   * get user agent
   *
   * @param request
   * @return
   */
  public static String getUserAgent(ServerHttpRequest request) {
    if (request == null) {
      return "";
    }
    return request.getHeaders().getFirst(HttpHeaders.USER_AGENT);
  }

  /**
   * make log from join point
   *
   * @param joinPoint join point.
   * @return
   */
  public static String makeLog(JoinPoint joinPoint) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    StringBuilder sb = new StringBuilder("\n");

    sb.append("ip : ").append(request.getRemoteAddr()).append("\n");
    sb.append("args : ").append(Arrays.toString(joinPoint.getArgs())).append("\n");

    sb.append("Controller : ").append(joinPoint.getTarget().getClass().getName()).append(".(").append(joinPoint.getTarget().getClass().getSimpleName()).append(".java:1) ").append(joinPoint.getSignature().getName()).append("\n");

    String uri = request.getRequestURI();
    if (uri != null) {
      sb.append("url : ").append(uri).append("\n");
    }

    Enumeration<String> e = request.getParameterNames();
    if (e.hasMoreElements()) {
      sb.append("Parameter   : ");
      while (e.hasMoreElements()) {
        String name = e.nextElement();
        String[] values = request.getParameterValues(name);
        if (values.length == 1) {
          sb.append(name).append("=").append(values[0]);
        } else {
          sb.append(name).append("[]={");
          for (int i = 0; i < values.length; i++) {
            if (i > 0) {
              sb.append(",");
            }
            sb.append(values[i]);
          }
          sb.append("}");
        }
        sb.append("  ");
      }
      sb.append("\n");
    }

    return sb.toString();
  }


  /**
   * get client ip address
   *
   * @param request
   * @return
   */
  public static String getClientIpAddress(ServerHttpRequest request) {
    if (request != null) {
      return "";
    }
    try {
      HttpHeaders httpHeaders = request.getHeaders();
      for (String header : WebConst.IP_HEADER_CANDIDATES) {
        String ip = httpHeaders.getFirst(header);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
          return ip;
        }
      }
      return request.getRemoteAddress().getAddress().getHostAddress();
    } catch (Exception e) {
      log.error("fail to get client ip", e);
    }

    return "";
  }

  /**
   * 动态注册Controller
   *
   * @param ctx             spring上下文
   * @param requestPath     请求路径
   * @param requestMethod   请求方法
   * @param controllerClass 控制器类
   * @param methodName      请求方法
   * @param parameterTypes  请求方法参数
   */
  public static void registerMapping(ApplicationContext ctx, String requestPath, RequestMethod requestMethod, Class<?> controllerClass, String methodName, Class<?>... parameterTypes) {
    if (requestMethod == null) {
      requestMethod = RequestMethod.GET;
    }
    RequestMappingInfo mappingInfo = RequestMappingInfo.paths(requestPath).methods(requestMethod).build();

    try {
      Method method = controllerClass.getClass().getMethod(methodName, parameterTypes);

      RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) ctx.getBean("requestMappingHandlerMapping");
      requestMappingHandlerMapping.registerMapping(mappingInfo, controllerClass, method);
    } catch (Exception e) {
      log.error("fail to register dynamic controller", e);
    }
  }

  /**
   * 取消之前注册的Controller
   *
   * @param ctx            spring上下文
   * @param controllerName controller名称
   */
  public static void unregisterMapping(ApplicationContext ctx, String controllerName) {
    RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) ctx.getBean("requestMappingHandlerMapping");
    Object controller = ctx.getBean(controllerName);
    if (controller == null) {
      log.warn("controller[{}] is not in container", controllerName);
      return;
    }
    Class<?> targetClass = controller.getClass();
    ReflectionUtils.doWithMethods(targetClass, method -> {
      Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
      try {
        Method createMappingMethod = RequestMappingHandlerMapping.class.getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
        createMappingMethod.setAccessible(true);
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
        if (requestMappingInfo != null) {
          requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
        }
      } catch (Exception e) {
        log.warn("fail to reflect unregister controller method", e);
      }
    }, ReflectionUtils.USER_DECLARED_METHODS);
  }

}
