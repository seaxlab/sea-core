package com.github.seaxlab.core.spring.web;

import com.github.seaxlab.core.spring.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/29
 * @since 1.0
 */
@Slf4j
public class WebFluxTest extends BaseSpringTest {

    @Test
    public void test17() throws Exception {

        WebClient webClient = WebClient.builder()
                                       .clientConnector(new ReactorClientHttpConnector(
                                               HttpClient.create().followRedirect(true)
                                       )).build();

        log.info("webclient={}", webClient);
    }
}
