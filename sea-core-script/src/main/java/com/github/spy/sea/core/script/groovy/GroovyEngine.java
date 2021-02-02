package com.github.spy.sea.core.script.groovy;

import com.github.spy.sea.core.security.util.Md5Util;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovySystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Groovy脚本引擎
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class GroovyEngine {

    private static final Map<String, GroovyClassDefinition> REGISTRY = new ConcurrentHashMap<>();

    private static GroovyClassLoader loader;

    public static GroovyClassLoader getLoader() {
        if (loader == null) {
            synchronized (GroovyEngine.class) {
                if (loader == null) {
                    loader = new GroovyClassLoader();
                }
            }
        }
        return loader;
    }


    public static Class<?> load(String filePath) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource resource = resolver.getResource(filePath);

        if (resource.exists()) {
            if (resource.isFile()) {
                try {
                    String fileText = Files.toString(resource.getFile(), Charsets.UTF_8);

                    return putIfAbsent(filePath, fileText);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return null;
    }

    /**
     * 取class
     *
     * @param key
     * @return
     */
    public static Class<?> get(String key) {
        if (REGISTRY.containsKey(key)) {
            GroovyClassDefinition def = REGISTRY.get(key);

            return def.getClazz();
        }
        log.warn("groovy key={} not exist", key);
        return null;
    }

    /**
     * 存储groovy class
     *
     * @param key
     * @param scriptText
     */
    public static Class<?> putIfAbsent(String key, String scriptText) {

        getLoader();

        if (REGISTRY.containsKey(key)) {
            GroovyClassDefinition def = REGISTRY.get(key);


            String newDigest = Md5Util.getDigest(scriptText);

            if (newDigest.equalsIgnoreCase(def.getDigest())) {

                log.info("two scriptText digest are the same");
            } else {
                try {
                    Class oldClass = def.getClass();
                    GroovySystem.getMetaClassRegistry().removeMetaClass(oldClass);
                } catch (Exception e) {
                    log.error("groovy remove meta class error", e);
                }

                def.setScriptText(scriptText);
                def.setDigest(newDigest);
                def.setClazz(loader.parseClass(scriptText));
            }
            return def.getClazz();
        } else {
            GroovyClassDefinition def = new GroovyClassDefinition();
            def.setKey(key);
            def.setScriptText(scriptText);
            def.setDigest(Md5Util.getDigest(scriptText));
            def.setClazz(loader.parseClass(scriptText));

            REGISTRY.put(key, def);

            return def.getClazz();
        }
    }


}
