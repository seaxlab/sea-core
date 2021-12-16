package com.github.spy.sea.core.loader;

import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.model.LinkedMultiValueMap;
import com.github.spy.sea.core.model.MultiValueMap;
import com.github.spy.sea.core.util.ClassUtil;
import com.github.spy.sea.core.util.PropertiesUtil;
import com.github.spy.sea.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * General purpose factory loading mechanism for internal use within the framework.
 * SeaFactoriesLoader loads and instantiates factories of a given type from "META-INF/sea.factories" files
 * which may be present in multiple JAR files in the classpath.
 * The sea.factories file must be in Properties format, where the key is the fully qualified name of the
 * interface or abstract class, and the value is a comma-separated list of implementation class names.
 * <pre>
 * For example:
 *      example.MyService=example.MyServiceImpl1,example.MyServiceImpl2
 * </pre>
 * where example.MyService is the name of the interface, and MyServiceImpl1 and MyServiceImpl2 are two implementations.
 *
 * @author spy
 * @version 1.0 2021/3/1
 * @since 1.0
 */
@Slf4j
public class SeaFactoriesLoader {
    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/sea.factories";


    private static final Log logger = LogFactory.getLog(SeaFactoriesLoader.class);

    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();


    private SeaFactoriesLoader() {
    }


    /**
     * Load and instantiate the factory implementations of the given type from
     * {@value #FACTORIES_RESOURCE_LOCATION}, using the given class loader.
     * <p>The returned factories are sorted through { AnnotationAwareOrderComparator}.
     * <p>If a custom instantiation strategy is required, use {@link #loadFactoryNames}
     * to obtain all registered factory names.
     *
     * @param factoryType the interface or abstract class representing the factory
     * @param classLoader the ClassLoader to use for loading (can be {@code null} to use the default)
     * @throws IllegalArgumentException if any factory implementation class cannot
     *                                  be loaded or if an error occurs while instantiating any factory
     * @see #loadFactoryNames
     */
    public static <T> List<T> loadFactories(Class<T> factoryType, ClassLoader classLoader) {
        Precondition.checkNotNull(factoryType, "'factoryType' must not be null");
        ClassLoader classLoaderToUse = classLoader;
        if (classLoaderToUse == null) {
            classLoaderToUse = SeaFactoriesLoader.class.getClassLoader();
        }
        List<String> factoryImplementationNames = loadFactoryNames(factoryType, classLoaderToUse);
        if (logger.isTraceEnabled()) {
            logger.trace("Loaded [" + factoryType.getName() + "] names: " + factoryImplementationNames);
        }
        List<T> result = new ArrayList<>(factoryImplementationNames.size());
        for (String factoryImplementationName : factoryImplementationNames) {
            result.add(instantiateFactory(factoryImplementationName, factoryType, classLoaderToUse));
        }
        // order by custom
        OrderComparator.sort(result);
        return result;
    }

    /**
     * Load the fully qualified class names of factory implementations of the
     * given type from {@value #FACTORIES_RESOURCE_LOCATION}, using the given
     * class loader.
     *
     * @param factoryType the interface or abstract class representing the factory
     * @param classLoader the ClassLoader to use for loading resources; can be
     *                    {@code null} to use the default
     * @throws IllegalArgumentException if an error occurs while loading factory names
     * @see #loadFactories
     */
    public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
        String factoryTypeName = factoryType.getName();
        return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
    }

    private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
        MultiValueMap<String, String> result = cache.get(classLoader);
        if (result != null) {
            return result;
        }

        try {
            Enumeration<URL> urls = (classLoader != null ?
                    classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
            result = new LinkedMultiValueMap<>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                URLConnection con = url.openConnection();
//                ResourceUtils.useCachesIfNecessary(con);
                InputStream inputStream = con.getInputStream();
                Properties properties = PropertiesUtil.load(inputStream);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    String factoryTypeName = ((String) entry.getKey()).trim();
                    for (String factoryImplementationName : StringUtils.split((String) entry.getValue(), ",")) {
                        result.add(factoryTypeName, factoryImplementationName.trim());
                    }
                }
            }
            cache.put(classLoader, result);
            return result;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load factories from location [" + FACTORIES_RESOURCE_LOCATION + "]", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T instantiateFactory(String factoryImplementationName, Class<T> factoryType, ClassLoader classLoader) {
        try {
            Class<?> factoryImplementationClass = ClassUtil.load(factoryImplementationName, false, classLoader);
            if (!factoryType.isAssignableFrom(factoryImplementationClass)) {
                throw new IllegalArgumentException(
                        "Class [" + factoryImplementationName + "] is not assignable to factory type [" + factoryType.getName() + "]");
            }
            Constructor constructor = ReflectUtil.getConstructor(factoryImplementationClass);
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (Throwable ex) {
            throw new IllegalArgumentException(
                    "Unable to instantiate factory class [" + factoryImplementationName + "] for factory type [" + factoryType.getName() + "]",
                    ex);
        }
    }
}
