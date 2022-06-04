package com.github.spy.sea.core.spring.component.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * encrypt in .yml/.properties
 *
 * <pre>
 *     .yml
 *     password: SEA_AES(xxxx) # xxxx is encrypted string.
 *
 * {@literal @}Bean
 * public EncryptPropertiesBeanFactoryPostProcessor encryptPropertiesBeanFactoryPostProcessor(ConfigurableEnvironment environment){
 *     return new EncryptPropertiesBeanFactoryPostProcessor(environment);
 * }
 * </pre>
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
public class EncryptPropertiesBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    private final ConfigurableEnvironment environment;

    public EncryptPropertiesBeanFactoryPostProcessor(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.stream()
                       .filter(ps -> !(ps instanceof PropertySourceWrapper))//
                       .map(item -> wrapperProperSource(item))
                       .collect(Collectors.toList())//
                       .forEach(ps -> propertySources.replace(ps.getName(), ps));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    //----------------- private ---------------------

    private <T> PropertySource<T> wrapperProperSource(PropertySource<T> propertySource) {
        if (Stream.of("ConfigurationPropertySourcesPropertySource", "ConfigFileApplicationListener$ConfigurationPropertySources")
                  .anyMatch(propertySource.getClass().getName()::endsWith)) {
            return propertySource;
        } else if (propertySource instanceof MapPropertySourceWrapper) {
            return (PropertySource<T>) new MapPropertySourceWrapper((MapPropertySource) propertySource);
        } else if (propertySource instanceof EnumerablePropertySourceWrapper) {
            return (PropertySource<T>) new EnumerablePropertySourceWrapper<T>((EnumerablePropertySource) propertySource);
        }

        return new NormalPropertySourceWrapper<>(propertySource);
    }
}
