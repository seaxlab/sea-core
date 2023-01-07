package com.github.seaxlab.core.spring.component.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.spring.component.json.annotation.JsonParam;
import com.github.seaxlab.core.spring.component.json.xss.JsonExplorer;
import com.github.seaxlab.core.spring.component.json.xss.XSSProcessor;
import com.github.seaxlab.core.spring.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Json Param argument resolver
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {

  public static final String CACHED_JSON_NAME = JsonParamArgumentResolver.class.getName() + "_JSON_RESULT";

  private final JsonParamAdviceChain adviceChain = new JsonParamAdviceChain();
  private final FastJsonHttpMessageConverter jsonMsgConverter = new FastJsonHttpMessageConverter();
  private ConfigurableBeanFactory configurableBeanFactory = null;
  private BeanExpressionContext expressionContext = null;


  public void setRequestBodyAdvice(List<Object> requestResponseBodyAdvice) {
    this.adviceChain.loadAdvice(requestResponseBodyAdvice);
  }

  public void setConfigurableBeanFactory(ConfigurableBeanFactory beanFactory) {
    this.configurableBeanFactory = beanFactory;
    this.expressionContext = (beanFactory != null ? new BeanExpressionContext(beanFactory, new RequestScope()) : null);
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    if (!MultipartResolutionDelegate.isMultipartArgument(parameter)) {
      JsonParam parameterAnnotation = parameter.getParameterAnnotation(JsonParam.class);
      if (parameterAnnotation != null && parameterAnnotation.enable()) {
        return true;
      }
      JsonParam methodAnnotation = parameter.getMethodAnnotation(JsonParam.class);
      if (methodAnnotation != null && methodAnnotation.enable()) {
        return true;
      }
      JsonParam classAnnotation = parameter.getDeclaringClass().getAnnotation(JsonParam.class);
      if (classAnnotation != null && classAnnotation.enable()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    parameter = parameter.nestedIfOptional();
    JsonParamConfig paramConfig = new JsonParamConfig(parameter);
    Type parameterType = parameter.getNestedGenericParameterType();
    String parameterName = StringUtils.defaultString(parameter.getParameterName(), Conventions.getVariableNameForParameter(parameter));

    boolean simpleValueType = parameterType instanceof Class && BeanUtils.isSimpleValueType((Class<?>) parameterType);
    JSON json = convertMessage(webRequest, mavContainer, parameter);
    Object arg = PathUtil.find(json, paramConfig.getPath());

    // check XSS
    if (paramConfig.isXss()) {
      arg = JsonExplorer.explore(arg, XSSProcessor.getProcessor()::process);
    }

    // check default value
    Object defaultValue = resolveEmbeddedValueAndExpressions(paramConfig.getDefaultValue());

    if (simpleValueType
      && defaultValue != null
      && (arg == null || (parameterType == String.class && StringUtils.isEmpty((String) arg)))) {
      arg = defaultValue;
    }

    if (binderFactory != null) {
      WebDataBinder binder = binderFactory.createBinder(webRequest, null, "");
      // 转换数据结构
      if (arg != null) {
        arg = convertObject(binder, arg, parameterType);
      }

      // 如果转换结果为空，则默认值进行二次转换
      if (arg == null && simpleValueType) {
        arg = convertObject(binder, defaultValue, parameterType);
      }

      // 自定义数据转换
      if (arg != null) {
        if (paramConfig.getConvertor() != null) {
          for (Class<? extends JsonParamFieldConvertor> clazz : paramConfig.getConvertor()) {
            JsonParamFieldConvertor convertor = SpringContextHolder.getBean(clazz);
            arg = convertor.convert(json, paramConfig.getHandleFieldName(), arg);
          }
        }
      }

      // 对象校验
      if (arg != null) {
        binder = binderFactory.createBinder(webRequest, arg, parameterName);

        // hibernate-validation
        validateIfApplicable(binder, parameter);

        // 自定义校验  //请求参数DTO实现此接口，进行简单校验
        if (arg instanceof BeanValidator) {
          ((BeanValidator) arg).validate();
        }
        if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
          throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
        }
      }
    }

    if (arg != null && !parameter.isOptional() && paramConfig.isRequired()) {
      throw new ServletRequestBindingException("Missing argument'" + parameterName + "' for method parameter of type" + parameter.getNestedParameterType().getSimpleName());
    }

    return adaptArgumentIfNecessary(arg, parameter);
  }

  //-------private -----

  private Object convertObject(WebDataBinder binder, Object val, Type type) {
    if (val != null) {
      try {
        // 处理泛型
        if (type instanceof ParameterizedType) {
          ParameterizedType parameterizedType = (ParameterizedType) type;
          Class rawType = (Class) parameterizedType.getRawType();
          Type[] typeArguments = parameterizedType.getActualTypeArguments();

          // 数组
          if (List.class.isAssignableFrom(rawType)) {
            if (val instanceof JSONArray) {
              JSONArray array = (JSONArray) val;
              if (typeArguments.length == 0) {
                return array;
              } else if (typeArguments.length == 1) {
                Collection list = newListInstance(rawType, array.size());
                for (Object obj : array) {
                  list.add(convertObject(binder, obj, typeArguments[0]));
                }
                return list;
              }
            }
          }

          // Map
          if (Map.class.isAssignableFrom(rawType)) {
            if (val instanceof JSONObject) {
              JSONObject jsonObj = (JSONObject) val;
              if (typeArguments.length == 0) {
                return jsonObj;
              } else if (typeArguments.length == 2) {
                Map map = newMapInstance(rawType, jsonObj.size());
                for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
                  map.put(convertObject(binder, entry.getKey(), typeArguments[0]), convertObject(binder, entry.getValue(), typeArguments[1]));
                }
                return map;
              }
            }
          }
          type = rawType;
        }

        Class<?> clazz = (Class<?>) type;
        if (clazz.isAssignableFrom(val.getClass())) {
          return val;
        }
        // 基本类型 String >Integer ,Integer> Boolean
        if (BeanUtils.isSimpleValueType(clazz)) {
          if (BeanUtils.isSimpleValueType(val.getClass())) {
            return binder.convertIfNecessary(val, clazz);
          }
        } else if (List.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz)) {
          if (val instanceof JSONArray) {
            JSONArray array = (JSONArray) val;
            Collection data = newListInstance(clazz, array.size());
            return data;
          }
        } else if (Map.class.isAssignableFrom(clazz)) {
          if (val instanceof JSONObject) {
            JSONObject obj = (JSONObject) val;
            Map map = newMapInstance(clazz, obj.size());
            map.putAll(obj);
            return map;
          }
        } else if (val instanceof JSONObject) {
          return ((JSON) val).toJavaObject(clazz);
        } else if (val instanceof JSONArray) {
          return ((JSON) val).toJavaObject(clazz);
        }
      } catch (Exception e) {
        log.error("can not convert type:'{}' to '{}', exception:", val.getClass(), type, e);
        ExceptionHandler.publishMsg("parameter parse error.");
      }
    }

    if (type instanceof Class) {
      Class<?> clazz = (Class<?>) type;
      if (List.class.isAssignableFrom(clazz)) {
        return newListInstance(clazz, 0);
      } else if (Map.class.isAssignableFrom(clazz)) {
        return newMapInstance(clazz, 0);
      }
    }
    log.warn("convert object null, plz check.");
    return null;
  }


  private Collection newListInstance(Class<?> type, int size) {
    if (type == List.class) {
      return new ArrayList(size);
    }
    if (type == Set.class) {
      return new HashSet(size);
    }

    try {
      return (List) type.getDeclaredConstructor(int.class).newInstance(size);
    } catch (Exception e) {
      log.error("exception", e);
    }
    try {
      return (List) type.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      log.error("exception", e);
    }
    log.warn("can not instance type of {}", type);
    return null;
  }

  private Map newMapInstance(Class<?> type, int size) {
    if (type == Map.class) {
      return new HashMap(size);
    }
    try {
      return (Map) type.getDeclaredConstructor(int.class).newInstance(size);
    } catch (Exception e) {
      log.error("exception", e);
    }
    try {
      return (Map) type.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      log.error("exception", e);
    }
    log.warn("can not instance type of {}", type);
    return null;
  }


  private Object adaptArgumentIfNecessary(Object arg, MethodParameter parameter) {
    if (parameter.getParameterType() == Optional.class) {
      if (arg == null
        || (arg instanceof Collection && ((Collection<?>) arg).isEmpty())
        || (arg instanceof Object[] && ((Object[]) arg).length == 0)) {
        return Optional.empty();
      } else {
        return Optional.of(arg);
      }
    }
    return arg;
  }


  /**
   * check validate obj
   *
   * @param binder
   * @param parameter
   */
  private void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
    Annotation[] annotations = parameter.getParameterAnnotations();
    for (Annotation anno : annotations) {
      Validated validatedAnno = AnnotationUtils.getAnnotation(anno, Validated.class);
      if (validatedAnno != null || anno.annotationType().getSimpleName().startsWith("Valid")) {
        Object hints = (validatedAnno != null ? validatedAnno.value() : AnnotationUtils.getValue(anno));
        Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
        binder.validate(validationHints);
        break;
      }
    }
  }

  /**
   * 检查 参数是否设置了binding result，如果没有则抛出异常
   *
   * @param binder
   * @param parameter
   * @return
   */

  private boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
    int i = parameter.getParameterIndex();
    Class<?>[] paramTypes = parameter.getExecutable().getParameterTypes();
    boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
    return !hasBindingResult;
  }


  private Object resolveEmbeddedValueAndExpressions(String value) {
    if (this.configurableBeanFactory == null || this.expressionContext == null) {
      return value;
    }
    String placeholdersResolved = this.configurableBeanFactory.resolveEmbeddedValue(value);
    BeanExpressionResolver expressionResolver = this.configurableBeanFactory.getBeanExpressionResolver();
    if (expressionResolver == null) {
      return value;
    }
    return expressionResolver.evaluate(placeholdersResolved, this.expressionContext);
  }


  /**
   * convert message
   *
   * @param webRequest
   * @param modelAndViewContainer
   * @param parameter
   * @return
   * @throws HttpMediaTypeNotSupportedException
   * @throws HttpMessageNotReadableException
   */
  private JSON convertMessage(NativeWebRequest webRequest, ModelAndViewContainer modelAndViewContainer, MethodParameter parameter) throws HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
    if (modelAndViewContainer != null) {
      Object cache = modelAndViewContainer.getModel().getAttribute(CACHED_JSON_NAME);
      if (cache instanceof JSON) {
        return (JSON) cache;
      }
    }

    HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
    Assert.state(servletRequest != null, "no http servlet request");
    ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);

    MediaType contentType;
    contentType = inputMessage.getHeaders().getContentType();

    if (contentType == null) {
      contentType = MediaType.APPLICATION_OCTET_STREAM;
    }

    Class<?> paramTargetType = JSON.class;
    Object body = null;
    try {
      EmptyBodyCheckingHttpInputMessage message = new EmptyBodyCheckingHttpInputMessage(inputMessage);
      Class<? extends HttpMessageConverter<?>> convertType = FastJsonHttpMessageConverter.class;
      if (jsonMsgConverter.canRead(paramTargetType, contentType)) {
        if (message.hasBody()) {
          HttpInputMessage msgToUse = this.adviceChain.beforeBodyRead(message, parameter, paramTargetType, convertType);
          body = jsonMsgConverter.read(paramTargetType, msgToUse);
          body = this.adviceChain.afterBodyRead(body, msgToUse, parameter, paramTargetType, convertType);
        } else {
          body = this.adviceChain.handleEmptyBody(null, message, parameter, paramTargetType, convertType);
        }
      }

      if (body != null) {
        // 缓存解析结果
        if (modelAndViewContainer != null) {
          modelAndViewContainer.addAttribute(CACHED_JSON_NAME, body);
        }
        webRequest.setAttribute(CACHED_JSON_NAME, body, RequestAttributes.SCOPE_REQUEST);
      }
      return (JSON) body;
    } catch (Exception e) {
      throw new HttpMessageNotReadableException("Error while reading input message", e, inputMessage);
    }
  }


  /**
   * refer to RequestResponseBodyMethodProcessor
   */
  private static class EmptyBodyCheckingHttpInputMessage implements HttpInputMessage {

    private final HttpHeaders headers;
    private final InputStream body;

    public EmptyBodyCheckingHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
      this.headers = inputMessage.getHeaders();
      InputStream inputStream = inputMessage.getBody();
      //TODO add comment.
      if (inputStream.markSupported()) {
        inputStream.mark(1);
        this.body = (inputStream.read() != -1 ? inputStream : null);
        inputStream.reset();
      } else {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
        int b = pushbackInputStream.read();
        if (b == -1) {
          this.body = null;
        } else {
          this.body = pushbackInputStream;
          pushbackInputStream.unread(b);
        }
      }
    }

    @Override
    public InputStream getBody() throws IOException {
      return (this.body != null ? this.body : StreamUtils.emptyInput());
    }

    @Override
    public HttpHeaders getHeaders() {
      return this.headers;
    }

    public boolean hasBody() {
      return this.body != null;
    }
  }
}
