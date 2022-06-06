package com.github.spy.sea.core.spring.component.json;

import com.github.spy.sea.core.spring.component.responseFile.ResponseFileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * argument and return value mvc config example
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class ArgumentAndReturnValueMvcConfig implements WebMvcConfigurer, BeanFactoryAware, InitializingBean {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private final JsonParamArgumentResolver argumentResolver = new JsonParamArgumentResolver();

    private final ResponseFileHandler responseFileHandler = new ResponseFileHandler();
    private ConfigurableBeanFactory beanFactory;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableBeanFactory) {
            this.beanFactory = (ConfigurableBeanFactory) beanFactory;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 目前只有通过反射才能获取所有的RequestBodyAdvice
            Field adviceField = RequestMappingHandlerMapping.class.getDeclaredField("requestResponseBodyAdvice");
            adviceField.setAccessible(true);
            argumentResolver.setRequestBodyAdvice((List<Object>) adviceField.get(requestMappingHandlerAdapter));

            // 移动首位
            List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
            handlers.add(responseFileHandler);
            List<HandlerMethodReturnValueHandler> existReturnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
            if (existReturnValueHandlers != null) {
                handlers.addAll(existReturnValueHandlers);
            }

            requestMappingHandlerAdapter.setReturnValueHandlers(handlers);
        } catch (Exception e) {
            log.error("cannot get RequestResponseBodyAdvices from RequestMappingHandlerAdapter");
        }

        argumentResolver.setConfigurableBeanFactory(beanFactory);
    }

}
