package com.github.spy.sea.core.example.config;

import com.github.spy.sea.core.spring.component.json.JsonParamArgumentResolver;
import com.github.spy.sea.core.spring.component.responseFile.ResponseFileHandler;
import com.github.spy.sea.core.spring.interceptor.RequestLogInterceptor;
import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @since 1.0
 */
@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer, BeanFactoryAware, InitializingBean {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private ConfigurableBeanFactory beanFactory;

    // custom

    private final JsonParamArgumentResolver jsonParamArgumentResolver = new JsonParamArgumentResolver();

    private final ResponseFileHandler responseFileHandler = new ResponseFileHandler();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        RequestLogInterceptor requestLogInterceptor = new RequestLogInterceptor();

        requestLogInterceptor.setIncludePayload(true);
        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/api/**");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jsonParamArgumentResolver);
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
            // get request response body advice by reflect
            Field adviceField = RequestMappingHandlerAdapter.class.getDeclaredField("requestResponseBodyAdvice");
            adviceField.setAccessible(true);
            jsonParamArgumentResolver.setRequestBodyAdvice((List<Object>) adviceField.get(requestMappingHandlerAdapter));

            // deal with response file at first
            List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
            handlers.add(responseFileHandler);

            List<HandlerMethodReturnValueHandler> oldHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
            if (ListUtil.isNotEmpty(oldHandlers)) {
                handlers.addAll(oldHandlers);
            }
            requestMappingHandlerAdapter.setReturnValueHandlers(handlers);

        } catch (Exception e) {
        }
        jsonParamArgumentResolver.setConfigurableBeanFactory(beanFactory);
    }
}
