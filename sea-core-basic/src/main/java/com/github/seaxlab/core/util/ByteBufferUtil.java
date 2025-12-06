package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * ByteBuffer util
 *
 * @author spy
 * @version 1.0 2019-08-08
 * @since 1.0
 */
@Slf4j
public final class ByteBufferUtil {

  private ByteBufferUtil() {
  }

  /**
   * 字节数组转化成16进制形式
   */
  public static String bytes2string(byte[] src) {
    StringBuilder sb = new StringBuilder();
    if (src == null || src.length == 0) {
      return null;
    }
    for (byte b : src) {
      int v = b & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        sb.append(0);
      }
      sb.append(hv.toUpperCase());
    }
    return sb.toString();
  }


  /**
   * 16进制字符串转化成字节数组
   */
  public static byte[] string2bytes(String hexString) {
    if (hexString == null || hexString.isEmpty()) {
      return new byte[0];
    }
    hexString = hexString.toUpperCase();
    int length = hexString.length() / 2;
    char[] hexChars = hexString.toCharArray();
    byte[] d = new byte[length];
    for (int i = 0; i < length; i++) {
      int pos = i * 2;
      d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
    }
    return d;
  }


  private static byte charToByte(char c) {
    return (byte) "0123456789ABCDEF".indexOf(c);
  }



  /**
   * release mappedByteBuffer memory
   *
   * @param mappedByteBuffer
   */
  public static void clean(MappedByteBuffer mappedByteBuffer) {
    if (mappedByteBuffer == null || !mappedByteBuffer.isDirect() || mappedByteBuffer.capacity() == 0) {
      return;
    }
    invoke(invoke(viewed(mappedByteBuffer), "cleaner"), "clean");
  }


  //---------------------------private--------------------------

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
    for (Method method : methods) {
      if (method.getName().equals("attachment")) {
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
