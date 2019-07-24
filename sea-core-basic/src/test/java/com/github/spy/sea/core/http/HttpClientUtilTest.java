package com.github.spy.sea.core.http;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.http.simple.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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

}
