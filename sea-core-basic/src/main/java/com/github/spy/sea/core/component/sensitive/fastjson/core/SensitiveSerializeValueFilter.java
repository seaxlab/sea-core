package com.github.spy.sea.core.component.sensitive.fastjson.core;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.spy.sea.core.component.sensitive.fastjson.annotation.SensitiveInfo;
import com.github.spy.sea.core.component.sensitive.strategy.IStrategy;
import com.github.spy.sea.core.lang.annotation.MergedAnnotation;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Slf4j
public class SensitiveSerializeValueFilter implements ValueFilter {

    //private static final Class<?>[] annos = new Class[]{
    //        SensitiveAddress.class
    //};

    @Override
    public Object process(Object object, String name, Object value) {

        try {
            MergedAnnotation mergedAnnotation = MergedAnnotation.from(object.getClass().getDeclaredField(name));
            final SensitiveInfo sensitiveInfoAnno = mergedAnnotation.getAnnotation(SensitiveInfo.class);

            //if (sensitiveInfoAnno == null) {
            //    // 获取字段上所有的注解
            //    Annotation[] annos = object.getClass().getDeclaredField(name).getAnnotations();
            //    if (annos == null || annos.length == 0) {
            //        return value;
            //    }
            //
            //    for (Annotation anno : annos) {
            //        if (anno.annotationType() == Deprecated.class &&
            //                anno.annotationType() == SuppressWarnings.class &&
            //                anno.annotationType() == Override.class &&
            //                anno.annotationType() == PostConstruct.class &&
            //                anno.annotationType() == PreDestroy.class &&
            //                anno.annotationType() == Resource.class &&
            //                anno.annotationType() == Resources.class &&
            //                anno.annotationType() == Generated.class &&
            //                anno.annotationType() == Target.class &&
            //                anno.annotationType() == Retention.class &&
            //                anno.annotationType() == Documented.class &&
            //                anno.annotationType() == Inherited.class
            //        ) {
            //
            //        } else {
            //            // JDK 原生注解不具备combo-annotation特性，因此这里再获取一次
            //            sensitiveInfoAnno = anno.annotationType().getAnnotation(SensitiveInfo.class);
            //            if (sensitiveInfoAnno != null) {
            //                break;
            //            }
            //        }
            //    }
            //}

            if (sensitiveInfoAnno == null) {
                return value;
            }

            if (!(value instanceof String)) {
                return value;
            }

            Class<? extends IStrategy> clazz = sensitiveInfoAnno.strategy();
            if (clazz == null) {
                log.warn("sensitive strategy class is null, plz check!");
                return value;
            }

            IStrategy strategy = clazz.getDeclaredConstructor().newInstance();

            if (sensitiveInfoAnno.begin() == 0 && sensitiveInfoAnno.end() == 0) {
                return strategy.desensitizationByPattern((String) value, sensitiveInfoAnno.pattern(), sensitiveInfoAnno.replaceChar());
            } else {
                return strategy.desensitization((String) value, sensitiveInfoAnno.begin(), sensitiveInfoAnno.end());
            }

        } catch (Exception e) {
            log.error("fastjson value filter exception", e);
        }

        return value;
    }
}
