package com.github.spy.sea.core.spring.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

/**
 * spring上下文
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    /**
     * 获取bean
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        checkCtx();

        return (T) ctx.getBean(name);
    }

    /**
     * 获取bean
     *
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        checkCtx();

        return ctx.getBean(requiredType);
    }

    /**
     * 调用spring注入新创建对象(根据属性名进行注入)
     *
     * @param ctx
     * @param bean
     */
    public static void autowireBean(ApplicationContext ctx, Object bean) {
        autowireBean(ctx, bean, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
    }

    /**
     * 调用spring注入新创建对象
     *
     * @param ctx
     * @param bean
     * @param autowireMode
     * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory
     */
    public static void autowireBean(ApplicationContext ctx, Object bean, int autowireMode) {
        if (bean == null || ctx == null) {
            return;
        }

        AutowireCapableBeanFactory factory = ctx.getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(bean, autowireMode, false);

        String beanName = ClassUtils.getUserClass(bean).getName();
        factory.initializeBean(bean, beanName);
    }


    /**
     * 调用spring注入新创建对象的相关属性(根据属性名进行注入)
     *
     * @param bean
     */
    public static void autowireBean(Object bean) {
        autowireBean(bean, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
    }

    /**
     * 调用spring注入新创建对象的相关属性
     *
     * @param bean
     * @param autowireMode
     * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory
     */
    public static void autowireBean(Object bean, int autowireMode) {

        checkCtx();

        if (bean == null) {
            return;
        }

        AutowireCapableBeanFactory factory = ctx.getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(bean, autowireMode, false);

        String beanName = ClassUtils.getUserClass(bean).getName();
        factory.initializeBean(bean, beanName);
    }


    private static void checkCtx() {
        if (ctx == null) {
            throw new IllegalStateException("applicationContext not injection, Please inject SpringContextHolder first.");
        }
    }

}
