package com.github.spy.sea.core.jvm.classloader;

import com.github.spy.sea.core.jvm.compiler.MemoryJavaCompiler;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * 内存的代码 class loader，参考自 oracle jdk
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
@Slf4j
public class ByteCodeClassLoader extends SecureClassLoader {
    /**
     * Map which represents class name and its compiled java object
     */
    private static final ConcurrentMap<String, Class<?>> javaFileObjectMap = new ConcurrentHashMap<>();
    private final String className;
    private final byte[] byteCode;

    /**
     * Creates a new {@code ByteCodeLoader} ready to load a class with the
     * given name and the given byte code.
     *
     * @param className The name of the class
     * @param byteCode  The byte code of the class
     */
    public ByteCodeClassLoader(String className, byte[] byteCode) {
        this.className = className;
        this.byteCode = byteCode;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (!name.equals(className)) {
            throw new ClassNotFoundException(name);
        }

        return defineClass(name, byteCode, 0, byteCode.length);
    }

    /**
     * Utility method for creating a new {@code ByteCodeLoader} and then
     * directly load the given byte code.
     *
     * @param className The name of the class
     * @param byteCode  The byte code for the class
     * @return A {@see Class} object representing the class
     */
    public static Class<?> load(String className, byte[] byteCode) {
        Function<String, Class<?>> classLoadFunc = (key) -> {
            try {
                return new ByteCodeClassLoader(key, byteCode).loadClass(className);
            } catch (ClassNotFoundException e) {
                log.error("load class exception.", e);
            }
            return null;
        };
        return javaFileObjectMap.computeIfAbsent(className, classLoadFunc);
    }

    /**
     * Utility method for creating a new {@code ByteCodeLoader} and then
     * directly load the given byte code.
     *
     * @param className  The name of the class
     * @param sourceCode The source code for the class with name {@code className}
     * @return A {@see Class} object representing the class
     */
    public static Class<?> load(String className, CharSequence sourceCode) {
        return load(className, MemoryJavaCompiler.compile(className, sourceCode));
    }

}
