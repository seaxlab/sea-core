package com.github.spy.sea.core.spring.controller;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回值修改
 *
 * @author spy
 * @version 1.0 2019-06-27
 * @since 1.0
 */
@Slf4j
//@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof BaseResult) {

            BaseResult result = (BaseResult) body;


            String requestId = MDC.get(CoreConst.MDC_REQ_ID);
            if (StringUtils.isEmpty(result.getRequestId())) {
                result.setRequestId(requestId);
            }

//            if (StringUtils.isEmpty(result.getTraceId())) {
//                result.setTraceId(TraceUtil.getTraceId());
//            }
//
//            if (StringUtils.isEmpty(result.getSpanId())) {
//                result.setSpanId(TraceUtil.getSpanId());
//            }

        }

        return body;
    }
}
