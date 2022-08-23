package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.spring.aop.advisor.DefaultPointCutAdvisor;
import com.github.seaxlab.core.spring.aop.advisor.LogCostPointCut;
import com.github.seaxlab.core.spring.aop.interceptor.LogCostMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/6
 * @since 1.0
 */
@Slf4j
@Configuration
public class SeaCoreConfig {

    @Value("${sea.fastjson.writeNullValue:false}")
    private Boolean writeNullValueFlag;

//    @Bean
//    @ConditionalOnClass(FastJsonHttpMessageConverter.class)
//    @ConditionalOnProperty(prefix = "sea", name = "fastjson.enable", havingValue = "true", matchIfMissing = true)
//    @ConditionalOnMissingBean
//    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//
//
//        //支持空值输出
//        if (writeNullValueFlag.booleanValue()) {
//            fastJsonConfig.setSerializerFeatures(
//                    SerializerFeature.PrettyFormat,
//                    SerializerFeature.QuoteFieldNames,
//                    SerializerFeature.IgnoreErrorGetter,
//                    SerializerFeature.WriteDateUseDateFormat,
//                    SerializerFeature.DisableCircularReferenceDetect,
//                    SerializerFeature.WriteMapNullValue,
//                    SerializerFeature.WriteNullBooleanAsFalse,
//                    SerializerFeature.WriteNullListAsEmpty,
//                    SerializerFeature.WriteNullNumberAsZero,
//                    SerializerFeature.WriteNullStringAsEmpty
//            );
//        } else {
//            fastJsonConfig.setSerializerFeatures(
//                    SerializerFeature.PrettyFormat,
//                    SerializerFeature.QuoteFieldNames,
//                    SerializerFeature.IgnoreErrorGetter,
//                    SerializerFeature.WriteDateUseDateFormat,
//                    SerializerFeature.DisableCircularReferenceDetect
//            );
//        }
//
//        //
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//
//        fastConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_JSON_UTF8));
//
//        return fastConverter;
//    }

    //private static final String DEFAULT_EXPRESSION_LOG_COST = "@annotation(com.github.seaxlab.core.spring.annotation.LogCost)";

    @Bean
    //@ConditionalOnMissingBean
    public PointcutAdvisor seaLogCostAdvisor() {
        log.info("init sea log cost advisor bean");

        //DynamicPointcutAdvisor advisor = new DynamicPointcutAdvisor(DEFAULT_EXPRESSION_LOG_COST);
        //advisor.setAdviceBeanName("seaLogCostPointcutAdvisor");
        //advisor.setAdvice(new LogCostMethodInterceptor());
        //advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);

        DefaultPointCutAdvisor advisor = new DefaultPointCutAdvisor(new LogCostPointCut());

        advisor.setAdviceBeanName("seaLogCostPointcutAdvisor");
        advisor.setAdvice(new LogCostMethodInterceptor());
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    //private static final String DEFAULT_EXPRESSION_PROFILER = "@annotation(com.github.seaxlab.core.component.perf.anno.Profiler)";

}
