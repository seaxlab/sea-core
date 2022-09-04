package com.github.seaxlab.core.example.servlet;

import com.github.seaxlab.core.model.annotation.Beta;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/18
 * @since 1.0
 */
@Slf4j
@Beta
public class SeaExampleServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        // this is not work
        for (Class<?> clazz : c) {
            if (!clazz.isInterface()) {
                try {
                    System.out.println(clazz);
                    Constructor<?> constructor = clazz.getConstructor();
                    Object instance = constructor.newInstance();
                    MyContainerInitializer containerInitializer = (MyContainerInitializer) instance;
                    containerInitializer.onStartup(ctx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(">>>>>>");
    }
}
