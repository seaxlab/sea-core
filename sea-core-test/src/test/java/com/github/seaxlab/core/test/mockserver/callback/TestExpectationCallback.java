package com.github.seaxlab.core.test.mockserver.callback;

import lombok.extern.slf4j.Slf4j;
import org.mockserver.mock.action.ExpectationResponseCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/23
 * @since 1.0
 */
@Slf4j
public class TestExpectationCallback implements ExpectationResponseCallback {

    public HttpResponse handle(HttpRequest httpRequest) {
        log.info("----callback handle...");
        if (httpRequest.getPath().getValue().endsWith("/callback")) {
            return httpResponse;
        } else {
            return notFoundResponse();
        }
    }

    public static HttpResponse httpResponse = response()
            .withStatusCode(200)
            .withBody("success.");
}
