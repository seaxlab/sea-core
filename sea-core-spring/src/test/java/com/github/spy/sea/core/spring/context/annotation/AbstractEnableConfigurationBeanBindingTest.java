package com.github.spy.sea.core.spring.context.annotation;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * Abstract Test cases for {@link EnableConfigurationBeanBinding}
 *
 * @since 1.0.4
 */
public abstract class AbstractEnableConfigurationBeanBindingTest {

    protected AnnotationConfigApplicationContext context;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(getClass());
        context.setEnvironment(new AbstractEnvironment() {
            @Override
            protected void customizePropertySources(MutablePropertySources propertySources) {
                ResourceLoader resourceLoader = new DefaultResourceLoader();
                ResourcePropertySource propertySource = null;
                try {
                    propertySource = new ResourcePropertySource("temp",
                            resourceLoader.getResource("classpath:/enable-configuration-bean-binding.properties"));
                } catch (IOException e) {
                }
                propertySources.addFirst(propertySource);
            }
        });
        context.refresh();
    }

    @After
    public void tearDown() {
        context.close();
    }
}
