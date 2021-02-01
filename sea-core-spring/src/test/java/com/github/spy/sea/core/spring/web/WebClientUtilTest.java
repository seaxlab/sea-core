package com.github.spy.sea.core.spring.web;

import com.github.spy.sea.core.spring.web.util.WebClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/31
 * @since 1.0
 */
@Slf4j
public class WebClientUtilTest {

    @Test
    public void testRaw() throws Exception {
        WebClient webClient = WebClient.builder()
                                       .baseUrl("http://www.baidu.com/")
                                       .exchangeStrategies(ExchangeStrategies.builder()
                                                                             .codecs(configurer -> configurer.defaultCodecs()
                                                                                                             .maxInMemorySize(-1))
                                                                             .build())
                                       .build();

        Mono<String> mono = webClient.get()
                                     .uri("/")
                                     .retrieve()
                                     .bodyToMono(String.class);

        log.info("mono content={}", mono.block());
    }

    @Test
    public void testSimple() throws Exception {

        WebClient webClient = WebClientUtil.getInstance("abc");

        Mono<String> mono = webClient.get()
                                     .uri("http://www.baidu.com/")
                                     .retrieve()
                                     .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), clientResponse -> Mono.empty())
                                     .bodyToMono(String.class);

        log.info("content={}", mono.block());
    }

}
