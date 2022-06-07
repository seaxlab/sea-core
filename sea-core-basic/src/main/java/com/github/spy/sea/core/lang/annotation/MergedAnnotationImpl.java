package com.github.spy.sea.core.lang.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/7
 * @since 1.0
 */
@Slf4j
public class MergedAnnotationImpl implements MergedAnnotation {

    private final Map<Class<? extends Annotation>, List<Annotation>> annotations;

    public MergedAnnotationImpl(final AnnotatedElement element) {
        this.annotations = AnnotationUtil.mergeAnnotation(element);
    }

    @Override
    public Map<Class<? extends Annotation>, List<Annotation>> annotations() {
        return this.annotations;
    }
}
