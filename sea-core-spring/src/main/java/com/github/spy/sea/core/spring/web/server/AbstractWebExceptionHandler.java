package com.github.spy.sea.core.spring.web.server;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.spring.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public abstract class AbstractWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable t) {
        log.error("web exception", t);
        ServerHttpResponse resp = exchange.getResponse();
//        if (t instanceof StopAndResponseException) {
//            StopAndResponseException ex = (StopAndResponseException) t;
//            if (ex.getData() != null) {
//                resp.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//                return resp.writeWith(Mono.just(resp.bufferFactory().wrap(ex.getData().toString().getBytes())));
//            }
//        }
//        if (t instanceof RedirectException) {
//            RedirectException ex = (RedirectException) t;
//            if (ex.getRedirectUrl() != null) {
//                resp.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//                resp.getHeaders().setLocation(URI.create(ex.getRedirectUrl()));
//                return Mono.empty();
//            }
//        }

        BaseResult result = BaseResult.failMsg("service error, please contact administrator.");

        return ResponseUtil.toJSON(resp, result);
    }
}
