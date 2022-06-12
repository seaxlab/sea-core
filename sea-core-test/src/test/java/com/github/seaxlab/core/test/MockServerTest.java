package com.github.seaxlab.core.test;

import com.github.seaxlab.core.test.mockserver.callback.TestExpectationCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.action.ExpectationForwardCallback;
import org.mockserver.model.Header;
import org.mockserver.model.HttpForward;
import org.mockserver.model.HttpRequest;

import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.Header.header;
import static org.mockserver.model.HttpClassCallback.callback;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/23
 * @since 1.0
 */
@Slf4j
public class MockServerTest extends AbstractCoreTest {

    private ClientAndServer mockServer;

    @Before
    public void setUp() {
        mockServer = ClientAndServer.startClientAndServer(1080);
    }

    @Test
    public void simple() throws Exception {
        mockServer
                .when(
                        request()
                                .withPath("/test")
                )
                .respond(
                        response()
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ message: 'incorrect username and password combination' }")
                                .withDelay(TimeUnit.SECONDS, 1)
                );

        // plz visit http://localhost:1080/test
    }

    @Test
    public void createExpectationForForward() {
        mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/index.html"),
                        exactly(1))
                .forward(
                        forward()
                                .withHost("www.mock-server.com")
                                .withPort(80)
                                .withScheme(HttpForward.Scheme.HTTP)
                );
    }

    @Test
    public void createExpectationForCallBack() {
        mockServer
                .when(request().withPath("/callback"))
                .respond(
                        callback()
                                .withCallbackClass(TestExpectationCallback.class)
                );
    }


    @Test
    public void forwardClassCallback() {
        mockServer
                .when(
                        request()
                                .withPath("/some.*"))
                .forward(
                        callback()
                                .withCallbackClass(TestExpectationForwardCallback.class)
                );
    }

    private static class TestExpectationForwardCallback implements ExpectationForwardCallback {

        @Override
        public HttpRequest handle(HttpRequest httpRequest) {
            log.info("----handle");
            return request()
                    .withPath(httpRequest.getPath())
                    .withMethod("GET")
                    .withHeaders(
                            header("x-callback", "test_callback_header"),
                            header("Content-Length", "a_callback_request".getBytes(UTF_8).length),
                            header("Connection", "keep-alive")
                    )
                    .withBody("a_callback_request");
        }
    }

    @After
    public void destroy() {
        sleepMinute(3);
        mockServer.stop();
    }
}
