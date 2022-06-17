package com.github.seaxlab.core.util;

import com.github.seaxlab.core.domain.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-16
 * @since 1.0
 */
@Slf4j
public class ReflectionUtilTest {


    @Test
    public void run17() throws Exception {
        Person p = new Person();

        ReflectUtil.write(p, "age", "11");
        ReflectUtil.write(p, "id", 12);

        log.info("p={}", p);
    }

    @Test
    public void run28() throws Exception {
        Person p = new Person();
        p.setId("12");
        p.setAge(1);

        Field f1 = FieldUtils.getField(p.getClass(), "id", true);
        Field f2 = FieldUtils.getField(p.getClass(), "age", true);

        log.info("f1.type={}", f1.getType().isAssignableFrom(String.class));
        log.info("f2.type={}", f2.getType());
    }

    @Test
    public void getFieldNameTest() throws Exception {
//        private boolean ;
//        private Boolean ;

        log.info("id={}", ReflectUtil.getFieldName(User::getId));
        log.info("isUsed={}", ReflectUtil.getFieldName(User::getIsUsed));
        log.info("isSuc={}", ReflectUtil.getFieldName(User::isSuc));
    }


    @Data
    static class Person {
        private String id;

        private Integer age;
    }
}
