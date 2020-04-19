package com.github.spy.sea.core.io.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * release mappedByteBuffer memory in direct Memory
 *
 * @author spy
 * @version 1.0 2019/10/28
 * @since 1.0
 */
@Slf4j
public class MmapUtil {

    private MmapUtil() {
    }

    /**
     * release mappedByteBuffer memory
     *
     * @param mappedByteBuffer
     */
    public static void clean(MappedByteBuffer mappedByteBuffer) {
        ByteBuffer buffer = mappedByteBuffer;
        if (buffer == null || !buffer.isDirect() || buffer.capacity() == 0) {
            return;
        }
        invoke(invoke(viewed(buffer), "cleaner"), "clean");
    }

    private static Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
            try {
                Method method = method(target, methodName, args);
                method.setAccessible(true);
                return method.invoke(target);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private static Method method(Object target, String methodName, Class<?>[] args)
            throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }

    private static ByteBuffer viewed(ByteBuffer buffer) {
        String methodName = "viewedBuffer";
        Method[] methods = buffer.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("attachment")) {
                methodName = "attachment";
                break;
            }
        }
        ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
        if (viewedBuffer == null) {
            return buffer;
        } else {
            return viewed(viewedBuffer);
        }
    }
}