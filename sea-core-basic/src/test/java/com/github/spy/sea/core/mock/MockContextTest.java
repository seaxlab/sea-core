package com.github.spy.sea.core.mock;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/17
 * @since 1.0
 */
@Slf4j
public class MockContextTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        int a = MockContext.get("abc", 1);
        boolean flag = MockContext.get("abc", null);

        log.info("a={}", a);
        log.info("flag={}", flag);
    }

    @Test
    public void noMockTest() throws Exception {
        boolean mockFlag = MockContext.hasFlag("sea.mock.his_query_user_info");

        log.info("mockFlag={}", mockFlag);
    }

    @Test
    public void mockSucTest() throws Exception {
        ThreadContext.put(CoreConst.DEFAULT_MOCK_KEY, "his_query_user_info");

        boolean mockFlag = MockContext.hasFlag("sea.mock.his_query_user_info");

        log.info("mockFlag={}", mockFlag);
    }

    @Test
    public void mockMultiTest() throws Exception {
        ThreadContext.put(CoreConst.DEFAULT_MOCK_KEY, "his_query_user_info,his_query_reg");

        boolean mockFlag = MockContext.hasFlag("sea.mock.his_query_reg");

        log.info("mockFlag={}", mockFlag);
    }
}
