package com.github.seaxlab.core.spring.context.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * The annotation composes the multiple {@link EnableConfigurationBeanBinding EnableConfigurationBeanBindings}
 *
 * @author x
 * @since 1.0.4
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConfigurationBeanBindingsRegister.class)
public @interface EnableConfigurationBeanBindings {

    /**
     * @return the array of {@link EnableConfigurationBeanBinding EnableConfigurationBeanBindings}
     */
    EnableConfigurationBeanBinding[] value();
}
