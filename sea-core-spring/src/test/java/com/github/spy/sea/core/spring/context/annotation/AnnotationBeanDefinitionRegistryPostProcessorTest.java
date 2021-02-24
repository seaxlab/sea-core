package com.github.spy.sea.core.spring.context.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.*;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * {@link AnnotationBeanDefinitionRegistryPostProcessor} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AnnotationBeanDefinitionRegistryPostProcessorTest.ServiceAnnotationBeanDefinitionRegistryPostProcessor.class,
        AnnotationBeanDefinitionRegistryPostProcessorTest.class
})
@Configuration
public class AnnotationBeanDefinitionRegistryPostProcessorTest {

    @ServiceExt
    static class MyService {
    }

    @Autowired
    private MyService myService;

    @Qualifier("stringBean")
    @Autowired
    private String stringBean;

    @Test
    public void test() {
        assertNotNull(myService);
        assertEquals("Hello,World", stringBean);
    }

    @Test
    public void testGetAnnotation() {
        assertNotNull(AnnotationBeanDefinitionRegistryPostProcessor.getAnnotation(MyService.class, ServiceExt.class));
    }

    @Test
    public void testResolveBeanClass() {

    }

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @interface ServiceExt {
    }

    static class ServiceAnnotationBeanDefinitionRegistryPostProcessor extends
            AnnotationBeanDefinitionRegistryPostProcessor {

        public ServiceAnnotationBeanDefinitionRegistryPostProcessor() {
            super(ServiceExt.class, ServiceExt.class);
        }

        @Override
        protected void registerSecondaryBeanDefinitions(ExposingClassPathBeanDefinitionScanner scanner,
                                                        Map<String, AnnotatedBeanDefinition> primaryBeanDefinitions,
                                                        String[] basePackages) {
            scanner.registerSingleton("stringBean", "Hello,World");

        }
    }
}
