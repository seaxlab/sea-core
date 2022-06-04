package com.github.spy.sea.core.spring.component.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class JsonParamAdviceChain implements RequestBodyAdvice {

    protected List<RequestBodyAdvice> requestBodyAdvices = new ArrayList<>();

    public void loadAdvice(List<Object> requestResponseBodyAdivce) {
        if (requestResponseBodyAdivce != null) {
            for (Object o : requestResponseBodyAdivce) {
                if (o instanceof RequestBodyAdvice) {
                    requestBodyAdvices.add((RequestBodyAdvice) o);
                }
            }
        }
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        for (RequestBodyAdvice advice : requestBodyAdvices) {
            if (advice.supports(parameter, targetType, converterType)) {
                inputMessage = advice.beforeBodyRead(inputMessage, parameter, targetType, converterType);
            }
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        for (RequestBodyAdvice advice : requestBodyAdvices) {
            if (advice.supports(parameter, targetType, converterType)) {
                body = advice.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
            }
        }
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        for (RequestBodyAdvice advice : requestBodyAdvices) {
            if (advice.supports(parameter, targetType, converterType)) {
                body = advice.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
            }
        }
        return body;
    }
}
