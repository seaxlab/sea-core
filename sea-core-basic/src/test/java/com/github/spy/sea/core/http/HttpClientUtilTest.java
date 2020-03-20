package com.github.spy.sea.core.http;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.http.simple.HttpClientUtil;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-24
 * @since 1.0
 */
@Slf4j
public class HttpClientUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String result = HttpClientUtil.get(IP_URL);

        log.info("result={}", result);
    }

    String str = "[{\"endpoint\": \"user-service\", \"metric\": \"test-metric\", \"timestamp\": 1, \"step\": 60, \"value\": 1, \"counterType\": \"GAUGE\", \"tags\": \"idc=lg,loc=beijing\"}]";

    @Test
    public void run27() throws Exception {
        BaseResult ret = HttpClientUtil.postJSONSafe("http://127.0.0.1:1988/v1/push", str);

        log.info("ret={}", ret);
    }

    @Test
    public void run36() throws Exception {
        byte[] by = str.getBytes(StandardCharsets.UTF_8);

        String str = new String(by, StandardCharsets.UTF_8);

        log.info("str={}", str);
    }

}
