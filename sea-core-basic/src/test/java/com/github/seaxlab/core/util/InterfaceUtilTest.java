package com.github.seaxlab.core.util;

import com.github.seaxlab.core.domain.User;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/6/3
 * @since 1.0
 */
@Slf4j
public class InterfaceUtilTest {

    interface IUserService<T> {

    }

    interface IC<A, B> {
    }

    class UserServiceImpl implements IUserService<User>, IC<User, User> {

    }


    @Test
    public void run25() throws Exception {

        Type[] genericInterfaces = UserServiceImpl.class.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    log.info("type is {}", genericType);
//                    log.info("class is {}", genericType.getClass());

                    log.info(" class is {}", TypeToken.of(genericType).getRawType());
                }
            }
        }
    }

    @Test
    public void run47() throws Exception {

        IUserService userService = new UserServiceImpl();

        Class<?> clazz = ReflectUtil.getSingleGenericClass(userService);

        log.info("clazz={}", clazz);
    }

    @Test
    public void run61() throws Exception {
        IUserService userService = new UserServiceImpl();

        Class<?>[] clazzArray = ReflectUtil.getAllGenericClass(userService);

        log.info("clazz={}", clazzArray);
    }


    @Test
    public void run43() throws Exception {
        Class<User> thisClass = null;
        Type type = UserServiceImpl.class.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            thisClass = (Class<User>) typeArguments[0];
        }
    }

}
