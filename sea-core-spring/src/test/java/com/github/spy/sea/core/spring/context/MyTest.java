package com.github.spy.sea.core.spring.context;

import com.github.spy.sea.core.spring.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.AntPathMatcher;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/15
 * @since 1.0
 */
@Slf4j
public class MyTest extends BaseSpringTest {

    @Test
    public void test17() throws Exception {
        AntPathMatcher matcher = new AntPathMatcher();
        log.info("{}", matcher.match("{uuid}", "xxxx"));
        Map<String, String> result = matcher.extractUriTemplateVariables("{uuid}", "xxx");
        log.info("{}", result);
    }

    public static void main(String[] args) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(A.class);
        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
        for (MethodDescriptor methodDescriptor : methodDescriptors) {
            System.out.println("method:" + methodDescriptor.getName());
            ParameterDescriptor[] params = methodDescriptor.getParameterDescriptors();
            if (params != null) {
                for (ParameterDescriptor param : params) {
                    System.out.println("param:" + param.getName());
                }
            }
        }

        Method[] methods = A.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("hello")) {
                LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
                String[] methodNames = discoverer.getParameterNames(method);
                for (String methodName : methodNames) {
                    System.out.println(methodName);
                }

            }

        }
    }

}
