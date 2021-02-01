package com.github.spy.sea.core.boot.autoconfigure;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.spy.sea.core.boot.autoconfigure.listener.ApplicationInitListener;
import com.github.spy.sea.core.spring.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * SeaCore 自动注册
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SeaProperties.class)
public class SeaCoreAutoConfigure {

    @Value("${sea.fastjson.writeNullValue:false}")
    private Boolean writeNullValueFlag;

    @Bean
    public ApplicationInitListener applicationInitListener() {
        return new ApplicationInitListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @ConditionalOnClass(FastJsonHttpMessageConverter.class)
    @ConditionalOnProperty(prefix = "sea", name = "fastjson.enable", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();


        //支持空值输出
        if (writeNullValueFlag.booleanValue()) {
            fastJsonConfig.setSerializerFeatures(
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.IgnoreErrorGetter,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero,
                    SerializerFeature.WriteNullStringAsEmpty
            );
        } else {
            fastJsonConfig.setSerializerFeatures(
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.IgnoreErrorGetter,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect
            );
        }

        //
        fastConverter.setFastJsonConfig(fastJsonConfig);

        fastConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON_UTF8));

        return fastConverter;
    }
}
