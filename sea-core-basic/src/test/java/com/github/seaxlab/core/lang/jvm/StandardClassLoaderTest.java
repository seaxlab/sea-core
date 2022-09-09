package com.github.seaxlab.core.lang.jvm;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.lang.jvm.classloader.StandardClassLoader;
import com.github.seaxlab.core.security.util.Base64Util;
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
        // 保存之前的classloader
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();

        String path = "/Users/shipengyan/spy/test/citic";

        StandardClassLoader standardExecutorClassLoader = new StandardClassLoader(path);
        Thread.currentThread().setContextClassLoader(standardExecutorClassLoader);

        Class<?> clazz = standardExecutorClassLoader.loadClass("com.lsy.baselib.crypto.util.Base64");
        Class<?> clazz2 = standardExecutorClassLoader.loadClass("com.lsy.baselib.crypto.util.Base64");

        log.info("clazz[{}], class loader is {}", clazz, clazz.getClassLoader());

        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("encode", byte[].class);

        String msg = "abc";

        Object ret = method.invoke(instance, msg.getBytes("utf-8"));
        log.info("ret encode={}", new String((byte[]) ret));


        Method method2 = clazz.getMethod("decode", byte[].class);

        Object ret2 = method2.invoke(instance, ret);
        log.info("ret decode={}", new String((byte[]) ret2));

        // 恢复之前的classloader
        Thread.currentThread().setContextClassLoader(oldClassLoader);
    }


    @Test
    public void run43() throws Exception {
        String msg = "abc";
        log.info("{}", Base64Util.encode(msg));
    }
}
