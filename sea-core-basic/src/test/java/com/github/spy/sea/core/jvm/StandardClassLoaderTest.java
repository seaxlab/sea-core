package com.github.spy.sea.core.jvm;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.jvm.classloader.StandardClassLoader;
import com.github.spy.sea.core.security.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/22
 * @since 1.0
 */
@Slf4j
public class StandardClassLoaderTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();

        String path = "/Users/shipengyan/spy/test/citic";

        StandardClassLoader standardExecutorClassLoader = new StandardClassLoader(path);
        Thread.currentThread().setContextClassLoader(standardExecutorClassLoader);

        Class<?> clazz = standardExecutorClassLoader.loadClass("com.lsy.baselib.crypto.util.Base64");
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("encode", byte[].class);

        String msg = "abc";

        Object ret = method.invoke(instance, msg.getBytes("utf-8"));
        log.info("ret encode={}", new String((byte[]) ret));


        Method method2 = clazz.getMethod("decode", byte[].class);

        Object ret2 = method2.invoke(instance, ret);
        log.info("ret decode={}", new String((byte[]) ret2));

        Thread.currentThread().setContextClassLoader(oldClassLoader);
    }


    @Test
    public void run43() throws Exception {
        String msg = "abc";
        log.info("{}", Base64Util.encode(msg));
    }
}
