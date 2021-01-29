package com.github.spy.sea.core.spring.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public final class ResponseUtil {

    private ResponseUtil() {
    }

    /**
     * 输出文本格式
     *
     * @param response
     * @param obj
     */
    public static Mono<Void> toText(ServerHttpResponse response, Object obj) {
        try {
            String content = obj == null ? "" : obj.toString();
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
            return response
                    .writeWith(Mono.just(response.bufferFactory().wrap(content.getBytes())));
        } catch (Exception e) {
            log.error("response error", e);
        }
        return Mono.empty();
    }

    /**
     * 输出JSON数据
     *
     * @param response
     * @param obj
     */
    public static Mono<Void> toJSON(ServerHttpResponse response, Object obj) {
        try {
            String content = obj == null ? "" : JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            return response
                    .writeWith(Mono.just(response.bufferFactory().wrap(content.getBytes())));
        } catch (Exception e) {
            log.error("response error", e);
        }
        return Mono.empty();
    }
}
