package com.github.spy.sea.core.spring.annotation;

import com.github.spy.sea.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;

/**
 * 属性级注解注入
 *
 * @author spy
 * @version 1.0 2021/2/23
 * @since 1.0
 */
@Slf4j
public abstract class AnnotationFieldProcessor<A extends Annotation>
        implements InstantiationAwareBeanPostProcessor, ApplicationContextAware, EnvironmentAware {

    protected ApplicationContext applicationContext;

    protected Environment environment;

    protected SimpleTypeConverter typeConverter = new SimpleTypeConverter();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            //获取泛型类型，在
            A annotation = field.getAnnotation(ClassUtil.resolveGenericType(this.getClass()));
            if (annotation == null) {
                return;
            }

            if (!isSupport(bean, beanName, annotation)) {
                log.info("current annotation is not support");
                return;
            }

            Object instance = getInstance(bean, beanName, annotation);
            if (instance == null) {
                log.warn("instance is null, so no inject.");
                return;
            }

            Object _value = typeConverter.convertIfNecessary(instance, field.getType()); // 转换成指定类型
            ReflectionUtils.makeAccessible(field);
            field.set(bean, _value);

            log.info("inject annotation [{}.{}]", bean.getClass().getName(), field.getName());
        });

        return true;
    }


    /**
     * 判断是否支持
     *
     * @param bean
     * @param beanName
     * @param annotation
     * @return true支持/false不支持
     */
    public abstract boolean isSupport(Object bean, String beanName, A annotation);

    /**
     * 具体对象实例
     *
     * @param bean
     * @param beanName
     * @param annotation
     * @return
     */
    public abstract Object getInstance(Object bean, String beanName, A annotation);
}
