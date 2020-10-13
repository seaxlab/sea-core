package com.github.spy.sea.core.spring.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/10/13
 * @since 1.0
 */
@Slf4j
public class SpringBeanUtilTest {

    @Test
    public void oneTest() throws Exception {

        User1 user1 = new User1();
        user1.setId(1L);
        user1.setName("user1");

        log.info("user2={}", SpringBeanUtil.convert(user1, User2.class));
    }


    @Test
    public void listTest() throws Exception {
        List<User1> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User1 user1 = new User1();
            user1.setId(Long.valueOf(i));
            user1.setName("name" + i);
            list.add(user1);
        }

        log.info("List<User2> = {}", SpringBeanUtil.convertList(list, User2.class));
    }

    @Test
    public void sourceNullTest() throws Exception {

//        java.lang.IllegalArgumentException: Source must not be null
//        at org.springframework.util.Assert.notNull(Assert.java:193)
//        at org.springframework.beans.BeanUtils.copyProperties(BeanUtils.java:654)
//        at org.springframework.beans.BeanUtils.copyProperties(BeanUtils.java:600)

        log.info("User2={}", SpringBeanUtil.convert(null, User2.class));
    }


    @Data
    public static class User1 implements Serializable {
        private Long id;
        private String name;
    }

    @Data
    public static class User2 implements Serializable {
        private Long id;
        private String name;
    }
}
