package com.github.seaxlab.core.lang.annotation;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/7
 * @since 1.0
 */
@Slf4j
public class AnnotationTest extends BaseCoreTest {


    @Test
    public void testClass() {
        final MergedAnnotation annotation = MergedAnnotation.from(AnnotationTest.class);
        final Parent parent = annotation.getAnnotation(Parent.class);

        log.info("parent.name()={}", parent.name());
    }

    @Test
    public void testMethod() throws Exception {
        Method method = UserEntity.class.getDeclaredMethod("getN");
        final MergedAnnotation annotation = MergedAnnotation.from(method);
        final Parent parent = annotation.getAnnotation(Parent.class);

        log.info("parent.name()={}", parent.name());
    }

    @Test
    public void testField() throws Exception {
        Field field = UserEntity.class.getDeclaredField("name");
        final MergedAnnotation annotation = MergedAnnotation.from(field);
        final Parent parent = annotation.getAnnotation(Parent.class);

        log.info("parent.name()={}", parent.name());
    }

    @Test
    public void testField2() throws Exception {
        Field field = UserEntity.class.getDeclaredField("code");
        final MergedAnnotation annotation = MergedAnnotation.from(field);
        final Parent parent = annotation.getAnnotation(Parent.class);

        log.info("parent.name()={}", parent.name());
    }


    @Children1
    public static class UserEntity {

        @Children1
        private String name;

        @Children2
        private String code;

        @Children1
        public void getN() {

        }
    }

}
