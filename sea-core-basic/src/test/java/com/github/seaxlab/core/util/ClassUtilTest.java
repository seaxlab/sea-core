package com.github.seaxlab.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public class ClassUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        Assert.assertEquals(ClassUtil.getFullClassName(String.class), "java.lang.String");
        Assert.assertEquals(ClassUtil.getClassName(String.class), "String");
    }

    @Test
    public void run25() throws Exception {
        String str = "1";

        Assert.assertEquals(true, ClassUtil.isOneOfClasses(str, String.class));
        Assert.assertEquals(false, ClassUtil.isOneOfClasses(str, JSONObject.class, JSONArray.class));
    }

    @Test
    public void run35() throws Exception {
        User user = new User();
        log.info("{}", ClassUtil.getClassName(user));
    }

    @Test
    public void resolveGenericTypeTest() throws Exception {
        // 这里只取了一个泛型
        Class<?> clazz = ClassUtil.resolveGenericType(Student.class);
        log.info("clazz={}", clazz);
    }

    @Test
    public void jdkGenericTypeTest() throws Exception {
        Student student = new Student();
        Class<?> clazz = student.getClass();
        clazz = Student.class;

        log.info("student parent class={}", clazz.getSuperclass());

        /**
         * getGenericSuperclass()获得带有泛型的父类
         * Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
         */
        Type type = clazz.getGenericSuperclass();
        log.info("generic super class={}", type);

        //ParameterizedType参数化类型，即泛型
        ParameterizedType p = (ParameterizedType) type;
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        Class c1 = (Class) p.getActualTypeArguments()[0];
        log.info("first={}", c1);
        Class c2 = (Class) p.getActualTypeArguments()[1];
        log.info("second={}", c2);
    }

    @Test
    public void test76() throws Exception {
        Class<?> clazz = ClassUtil.resolveGenericType(AA.class);
        log.info("clazz={}", clazz);

        AA aa = new AA();
        aa.init();
    }


    @Test
    public void testCheckDumplicate() throws Exception {

        boolean ret = ClassUtil.checkDuplicate(Logger.class);
        log.info("has duplicate class ret={}", ret);
    }


    private class Person<T1, T2> {

    }

    private class Student extends Person<Integer, Boolean> {
    }


    private abstract class A1<T1> {

        public void init() {
            Class<?> clazz = ClassUtil.resolveGenericType(getClass());
            log.info("clazz={}", clazz);
        }
    }

    private class AA extends A1<String> {
    }
}
