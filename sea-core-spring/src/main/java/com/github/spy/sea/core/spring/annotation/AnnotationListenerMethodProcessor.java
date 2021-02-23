package com.github.spy.sea.core.spring.annotation;

import com.github.spy.sea.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import static java.lang.reflect.Modifier.*;

/**
 * 方法级注解进行解析
 * 属性级注解请使用InstantiationAwareBeanPostProcessor进行处理
 *
 * @author spy
 * @version 1.0 2021/2/22
 * @since 1.0
 */
@Slf4j
public abstract class AnnotationListenerMethodProcessor<A extends Annotation>
        implements ApplicationListener<ContextRefreshedEvent> {

    private final Class<A> annotationType;

    public AnnotationListenerMethodProcessor() {
        this.annotationType = ClassUtil.resolveGenericType(getClass());
    }

    /**
     * Must be
     * <ul>
     * <li><code>public</code></li>
     * <li>not <code>static</code></li>
     * <li>not <code>abstract</code></li>
     * <li>not <code>native</code></li>
     * <li><code>void</code></li>
     * </ul>
     *
     * @param method {@link Method}
     * @return if obey above rules , return <code>true</code>
     */
    static boolean isListenerMethod(Method method) {

        int modifiers = method.getModifiers();

        Class<?> returnType = method.getReturnType();

        return isPublic(modifiers) && !isStatic(modifiers) && !isNative(modifiers)
                && !isAbstract(modifiers) && void.class.equals(returnType);
    }

    @Override
    public final void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("context refresh event.");
        // Retrieve ApplicationContext from ContextRefreshedEvent
        ApplicationContext applicationContext = event.getApplicationContext();
        // Select those methods from all beans that annotated
        processBeans(applicationContext);
    }

    private void processBeans(ApplicationContext applicationContext) {

        Map<String, Object> beansMap = applicationContext.getBeansOfType(Object.class,
                false, false);

        processBeans(beansMap, applicationContext);
    }

    private void processBeans(Map<String, Object> beansMap, ApplicationContext applicationContext) {

        for (Map.Entry<String, Object> entry : beansMap.entrySet()) {
            String beanName = entry.getKey();
            Object bean = entry.getValue();
            // Bean type
            if (bean != null) {
                Class<?> beanClass = AopUtils.getTargetClass(bean);
                processBean(beanName, bean, beanClass, applicationContext);
            }
        }

    }

    /**
     * Select those methods from bean that annotated
     *
     * @param beanName           Bean name
     * @param bean               Bean object
     * @param beanClass          the {@link Class} of Bean
     * @param applicationContext
     */
    private void processBean(final String beanName, final Object bean,
                             final Class<?> beanClass, final ApplicationContext applicationContext) {

        ReflectionUtils.doWithMethods(beanClass, method -> {
            A annotation = AnnotationUtils.getAnnotation(method, annotationType);
            if (annotation != null && isCandidateMethod(bean, beanClass, annotation, method, applicationContext)) {
                processListenerMethod(beanName, bean, beanClass, annotation, method, applicationContext);
            }
        }, method -> isListenerMethod(method));

    }

    /**
     * Process Listener Method when
     * {@link #isCandidateMethod(Object, Class, Annotation, Method, ApplicationContext)}
     * returns <code>true</code>
     *
     * @param beanName           Bean name
     * @param bean               Bean object
     * @param beanClass          Bean Class
     * @param annotation         Annotation object
     * @param method             Method
     * @param applicationContext ApplicationContext
     */
    protected abstract void processListenerMethod(String beanName, Object bean,
                                                  Class<?> beanClass, A annotation, Method method,
                                                  ApplicationContext applicationContext);

    /**
     * Subclass could override this method to determine current method is candidate or not
     *
     * @param bean               Bean object
     * @param beanClass          Bean Class
     * @param annotation         Annotation object
     * @param method             Method
     * @param applicationContext ApplicationContext
     * @return <code>true</code> as default
     */
    protected boolean isCandidateMethod(Object bean, Class<?> beanClass, A annotation,
                                        Method method, ApplicationContext applicationContext) {
        return true;
    }
}
