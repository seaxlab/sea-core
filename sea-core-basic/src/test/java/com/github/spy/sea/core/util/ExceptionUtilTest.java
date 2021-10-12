package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/3
 * @since 1.0
 */
@Slf4j
public class ExceptionUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        InvocationTargetException e = new InvocationTargetException(new RuntimeException("abc"));

        log.info("e.msg={}", e.getMessage());
        log.info("error msg={}", ExceptionUtil.getStackTrace(e));

        e = new InvocationTargetException(new RuntimeException("abc"), "cc");

        log.info("e.msg={}", e.getMessage());
        log.info("error msg={}", ExceptionUtil.getStackTrace(e));
    }


    @Test
    public void run34() throws Exception {
        log.info("{}", ExceptionUtils.getStackTrace(new NullPointerException()));
    }

    @Test
    public void test40() throws Exception {
        String str = "";

        String[] values = str.split(",", 10);
        log.info("values={}", values);
    }


    @Test
    public void testGetMsgN() throws Exception {

        SocketTimeoutException e = new SocketTimeoutException("This is socket timeout");

        String msg = ExceptionUtil.getMsgN(e, 10);
        log.info("msg={}", msg);

        log.info("msg={}", ExceptionUtil.getMsg1(e));

    }
}
