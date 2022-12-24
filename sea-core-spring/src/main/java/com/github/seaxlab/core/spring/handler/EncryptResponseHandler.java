package com.github.seaxlab.core.spring.handler;

import com.github.seaxlab.core.model.layer.encrypt.EncryptResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 加密返回值处理
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class EncryptResponseHandler implements HandlerMethodReturnValueHandler {
  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), EncryptResponse.class)
      || returnType.hasMethodAnnotation(EncryptResponse.class);
  }

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
    log.info("EncryptResponseHandler handle return value.");

    //TODO more
//        必须设置
//        mavContainer.setRequestHandled(true);
  }
}
