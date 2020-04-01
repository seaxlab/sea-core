package com.github.spy.sea.core.spring.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
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
     * create bean in spring application context
     *
     * @param ctx
     * @param bean
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
        // 通过BeanDefinitionBuilder创建bean定义
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(PayClient.class);
        // 注册bean
        beanFactory.registerSingleton(beanName, bean);
    }

    /**
     * 调用spring注入新创建对象(根据属性名进行注入)
     * note: 为bean中的属性注入
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
