package com.github.spy.sea.core.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.AliasRegistry;

import java.util.List;

import static java.beans.Introspector.decapitalize;
import static java.lang.String.format;
import static org.springframework.core.io.support.SpringFactoriesLoader.loadFactoryNames;
import static org.springframework.util.ClassUtils.getShortName;
import static org.springframework.util.ClassUtils.resolveClassName;
import static org.springframework.util.ObjectUtils.containsElement;
import static org.springframework.util.StringUtils.hasText;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/24
 * @since 1.0
 */
@Slf4j
public class BeanRegisterUtil {


    /**
     * Register Infrastructure Bean
     *
     * @param beanDefinitionRegistry {@link BeanDefinitionRegistry}
     * @param beanType               the type of bean
     * @param beanName               the name of bean
     * @return if it's a first time to register, return <code>true</code>, or <code>false</code>
     */
    public static boolean registerInfrastructureBean(BeanDefinitionRegistry beanDefinitionRegistry,
                                                     String beanName,
                                                     Class<?> beanType) {

        boolean registered = false;

        if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
            registered = true;

            if (log.isInfoEnabled()) {
                log.info("The Infrastructure bean definition [" + beanDefinition
                        + "with name [" + beanName + "] has been registered.");
            }
        }

        return registered;
    }

    /**
     * Detect the alias is present or not in the given bean name from {@link AliasRegistry}
     *
     * @param registry {@link AliasRegistry}
     * @param beanName the bean name
     * @param alias    alias to test
     * @return if present, return <code>true</code>, or <code>false</code>
     */
    public static boolean hasAlias(AliasRegistry registry, String beanName, String alias) {
        return hasText(beanName) && hasText(alias) && containsElement(registry.getAliases(beanName), alias);
    }


    /**
     * Register the beans from {@link SpringFactoriesLoader#loadFactoryNames(Class, ClassLoader) SpringFactoriesLoader}
     *
     * @param registry       {@link BeanDefinitionRegistry}
     * @param factoryClasses The factory classes to register
     * @return the count of beans that are succeeded to be registered
     * @since 1.0.7
     */
    public static int registerSpringFactoriesBeans(BeanDefinitionRegistry registry, Class<?>... factoryClasses) {
        int count = 0;

        ClassLoader classLoader = registry.getClass().getClassLoader();

        for (int i = 0; i < factoryClasses.length; i++) {
            Class<?> factoryClass = factoryClasses[i];
            List<String> factoryImplClassNames = loadFactoryNames(factoryClass, classLoader);
            for (String factoryImplClassName : factoryImplClassNames) {
                Class<?> factoryImplClass = resolveClassName(factoryImplClassName, classLoader);
                String beanName = decapitalize(getShortName(factoryImplClassName));
                if (registerInfrastructureBean(registry, beanName, factoryImplClass)) {
                    count++;
                } else {
                    if (log.isWarnEnabled()) {
                        log.warn(format("The Factory Class bean[%s] has been registered with bean name[%s]",
                                factoryImplClassName, beanName));
                    }
                }
            }
        }

        return count;
    }
}
