package com.github.seaxlab.core.lang.jvm;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.lang.jvm.classloader.NetworkClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/4
 * @since 1.0
 */
@Slf4j
public class NetworkClassLoaderTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {
    try {
      // 测试加载网络中的class文件
      String rootUrl = "http://localhost:8080/httpweb/classes";
      String className = "org.classloader.simple.NetClassLoaderSimple";
      NetworkClassLoader ncl1 = new NetworkClassLoader(rootUrl);
      NetworkClassLoader ncl2 = new NetworkClassLoader(rootUrl);
      Class<?> clazz1 = ncl1.loadClass(className);
      Class<?> clazz2 = ncl2.loadClass(className);
      Object obj1 = clazz1.newInstance();
      Object obj2 = clazz2.newInstance();
      clazz1.getMethod("setNetClassLoaderSimple", Object.class).invoke(obj1, obj2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
