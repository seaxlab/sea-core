package com.github.seaxlab.core.lang.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * network class loader.
 *
 * @author spy
 * @version 1.0 2020/9/4
 * @since 1.0
 */
@Slf4j
public class NetworkClassLoader extends ClassLoader {

    private String rootUrl;

    public NetworkClassLoader(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // this.findLoadedClass(name); // 父类已加载
        Class clazz = null;

        // 根据类的二进制名称,获得该class文件的字节码数组
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        // 将class的字节码数组转换成Class类的实例
        clazz = defineClass(name, classData, 0, classData.length);
        return clazz;
    }

    private byte[] getClassData(String name) {
        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024 * 4];
            int len = -1;
            is = url.openStream();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                stream.write(buff, 0, len);
            }
            return stream.toByteArray();
        } catch (Exception e) {
            log.error("fail to get class data", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("io exception", e);
                }
            }
        }
        return null;
    }

    private String classNameToPath(String name) {
        return rootUrl + "/" + name.replace(".", "/") + ".class";
    }
}
