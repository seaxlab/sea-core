package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.test.AbstractCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/2/28
 * @since 1.0
 */
@Slf4j
public class ThreadContextTest extends AbstractCoreTest {

    @Test
    public void run17() throws Exception {

        Boolean hit = ThreadContext.get("abc"); //ok
        boolean hit2 = ThreadContext.get("abc"); // throw NPE
    }


    @Test
    public void noMockTest() throws Exception {
        boolean mockFlag = ThreadContext.getMockFlag("sea.mock.his_query_user_info");

        log.info("mockFlag={}", mockFlag);
    }

    @Test
    public void mockSucTest() throws Exception {
        ThreadContext.put(CoreConst.DEFAULT_MOCK_KEY, "his_query_user_info");

        boolean mockFlag = ThreadContext.getMockFlag("sea.mock.his_query_user_info");

        log.info("mockFlag={}", mockFlag);
    }

    @Test
    public void mockMultiTest() throws Exception {
        ThreadContext.put(CoreConst.DEFAULT_MOCK_KEY, "his_query_user_info,his_query_reg");

        boolean mockFlag = ThreadContext.getMockFlag("sea.mock.his_query_reg");

        log.info("mockFlag={}", mockFlag);
    }
}
