package com.github.spy.sea.core.http;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.http.simple.HttpAsyncClientUtil;
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
public class HttpAsyncClientUtilTest extends BaseCoreTest {


    @Test
    public void run18() throws Exception {

        String result = HttpAsyncClientUtil.get(IP_URL);
        log.info("result={}", result);

    }


    @Test
    public void run29() throws Exception {
        for (int i = 0; i < 100; i++) {
            run18();
        }
    }

}
