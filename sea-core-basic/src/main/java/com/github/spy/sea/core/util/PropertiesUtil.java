package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/24
 * @since 1.0
 */
@Slf4j
public final class PropertiesUtil {

    private PropertiesUtil() {
    }

    /**
     * load from file
     *
     * @param path
     * @return
     */
    public static Properties load(String path) {
        try {
            InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
            if (in == null) {
                log.info("load properties [{}] not exist", path);
                return new Properties();
            }
            Properties pros = new Properties();
            pros.load(in);
            return pros;
        } catch (Exception e) {
            log.error("fail to load properties file", e);
        }

        return new Properties();
    }

    /**
     * load input stream.
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static Properties load(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new Properties();
        }

        Properties props = new Properties();
        props.load(inputStream);
        return props;
    }


    /**
     * load into Map
     *
     * @param path
     * @return
     */
    public static Map<String, String> loadForMap(String path) {
        Map<String, String> map = new HashMap<>();
        Properties pros = load(path);
        Enumeration en = pros.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            map.put(key, pros.getProperty(key));
        }
        return map;
    }

    /**
     * param str to properties.
     *
     * @param paramStr
     * @return
     */
    public static Properties parse(String paramStr) {
        Properties props = new Properties();

        String[] values = paramStr.split("&");

        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            String[] kv = value.split("=");
            if (kv.length == 1) {
                props.setProperty(kv[0], "");
            } else if (kv.length == 2) {
                props.setProperty(kv[0], kv[1]);
            }
        }

        return props;
    }

    /**
     * write properties file.
     *
     * @param properties
     * @param fileName
     * @return
     */
    public static boolean write(Properties properties, String fileName) {
        return write(properties, fileName, null);
    }

    /**
     * write properties
     *
     * @param properties
     * @param fileName
     * @return
     */
    public static boolean write(Properties properties, String fileName, String comment) {
        boolean successFlag = true;

        FileOutputStream out = null;
        OutputStreamWriter writer = null;
        try {
            out = new FileOutputStream(fileName);
            writer = new OutputStreamWriter(out, Charset.forName("UTF-8"));
            properties.store(writer, comment);
        } catch (IOException e) {
            successFlag = false;
            log.error("io exception", e);
        } finally {
            IOUtil.close(writer);
            IOUtil.close(out);
        }

        return successFlag;
    }

}
