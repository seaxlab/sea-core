package com.github.seaxlab.core.spring.context;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

/**
 * spring上下文
 *
 * <pre>
 *     <bean class="com.github.seaxlab.core.spring.context.SpringContextHolder"/>
 *  or
 *      @Bean
 *      public SpringContextHolder springContextHolder(){
 *          return new SpringContextHolder();
 *      }
 * </pre>
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        checkCtx();
        return ctx;
    }

    /**
     * 获取bean， 如果存在则会抛出异常
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
     * get bean by safely
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBeanSafe(String name) {
        try {
            return getBean(name);
        } catch (Exception e) {
            log.warn("no this bean[{}]", name);
        }
        return null;
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
     * get bean by safe.
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBeanSafe(Class<T> requiredType) {
        try {
            return getBean(requiredType);
        } catch (NoSuchBeanDefinitionException ee) {
            log.warn("no this bean[{}]", requiredType.getName());
        }
        return null;
    }

    /**
     * create single bean in current context
     *
     * @param bean
     */
    public static void createSingleBean(Object bean) {
        checkCtx();

        createSingleBean(ctx, bean);
    }

    /**
     * create bean in spring application context
     *
     * @param ctx
     * @param bean class instance
     */
    public static void createSingleBean(ApplicationContext ctx, Object bean) {
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ctx;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // com.xxx.HelloService
        String beanName = ClassUtils.getUserClass(bean).getName();
        if (beanFactory.containsBeanDefinition(beanName)) {
            beanFactory.removeBeanDefinition(beanName);
            log.warn("beanName={} has exist, so remove it");
        }

        beanFactory.registerSingleton(beanName, bean);
    }

    /**
     * create single bean
     *
     * @param clazz
     */
    public static void createSingleBean(Class<?> clazz) {
        checkCtx();
        createSingleBean(ctx, clazz);
    }

    /**
     * create single bean
     *
     * @param ctx
     * @param clazz
     */
    public static void createSingleBean(ApplicationContext ctx, Class<?> clazz) {
        Preconditions.checkNotNull(clazz, "clazz cannot be null.");

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ctx;

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // com.xxx.HelloService
        String beanName = ClassUtils.getUserClass(clazz).getName();
        if (beanFactory.containsBeanDefinition(beanName)) {
            beanFactory.removeBeanDefinition(beanName);
            log.warn("beanName={} has exist, so remove it");
        }
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        // rawBeanDefinition is unvalidated
        // beanDefinition is validated.
        beanFactory.registerSingleton(beanName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    /**
     * create bean
     *
     * @param beanName
     * @param beanDefinition
     * @see BeanDefinitionBuilder
     */
    public static void createBean(String beanName, BeanDefinition beanDefinition) {
        checkCtx();
        createBean(ctx, beanName, beanDefinition);
    }

    /**
     * create bean
     *
     * @param ctx
     * @param beanDefinition
     * @see BeanDefinitionBuilder
     */
    public static void createBean(ApplicationContext ctx, String beanName, BeanDefinition beanDefinition) {

        Preconditions.checkNotNull(beanDefinition, "clazz cannot be null.");
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ctx;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        if (beanFactory.containsBeanDefinition(beanName)) {
            beanFactory.removeBeanDefinition(beanName);
            log.warn("beanName={} has exist, so remove it");
        }
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 调用spring注入新创建对象(根据属性名进行注入)
     * <font color="red">
     * 为bean中的属性注入依赖
     * </font>
     *
     * @param ctx
     * @param bean
     */
    public static void autowireBean(ApplicationContext ctx, Object bean) {
        autowireBean(ctx, bean, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
    }

    /**
     * 为bean中的属性注入依赖
     *
     * @param ctx
     * @param bean
     * @param autowireMode
     * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory
     */
    public static void autowireBean(ApplicationContext ctx, Object bean, int autowireMode) {
        if (bean == null || ctx == null) {
            log.warn("ctx or bean is null");
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
     * <font color="red">
     * 为bean中的属性注入依赖
     * </font>
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

    /**
     * get object provider
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ObjectProvider<T> getBeanProvider(Class<T> clazz) {
        return getApplicationContext().getBeanProvider(clazz);
    }

    /**
     * get object provider.
     *
     * @param resolvableType
     * @param <T>
     * @return
     */
    public static <T> ObjectProvider<T> getBeanProvider(ResolvableType resolvableType) {
        return getApplicationContext().getBeanProvider(resolvableType);
    }


    /**
     * 发布一个事件
     *
     * @param event
     */
    public static void publish(ApplicationEvent event) {
        getApplicationContext().publishEvent(event);
    }


    private static void checkCtx() {
        if (ctx == null) {
            throw new IllegalStateException("applicationContext not injection, Please inject SpringContextHolder first.");
        }
    }

}
