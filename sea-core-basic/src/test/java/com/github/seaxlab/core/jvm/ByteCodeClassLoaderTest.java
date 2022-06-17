package com.github.seaxlab.core.jvm;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.jvm.classloader.ByteCodeClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
@Slf4j
public class ByteCodeClassLoaderTest extends BaseCoreTest {
    @Test
    public void test16() throws Exception {
        String className = "Foo";
        String sourceCode = "public class " + className + " {" +
                "    public void bar() {" +
                "        System.out.println(\"Hello from bar !\");" +
                "    }" +
                "}";
        Class<?> clazz = ByteCodeClassLoader.load(className, sourceCode);
        Assert.assertEquals(className, clazz.getName());
    }
}
