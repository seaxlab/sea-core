package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/16
 * @since 1.0
 */
@Slf4j
public class MapUtilTest extends BaseCoreTest {

    @Test
    public void cloneTest() throws Exception {
        List<Teacher> list = new ArrayList<>();
        Teacher t = new Teacher();
        t.setName("smith");
        list.add(t);


        Map<String, Object> map = new HashMap<>();
        map.put("key", 11);
        map.put("teachers", list);

        Map newMap = MapUtil.clone(map);
        log.info("{}", newMap);

        List<Teacher> list2 = (List<Teacher>) newMap.get("teachers");

        Teacher t2 = list2.get(0);

        t2.setName("2222");
        log.info("map={}", map);
        log.info("newMap={}", newMap);

    }

    @Data
    private class Teacher implements Serializable {
        private String name;
        private List<Student> students;
    }

    @Data
    private class Student implements Serializable {
        private Long id;
    }


    @Test
    public void run62() throws Exception {
        log.info("{}", "ac?".indexOf('?'));
    }

    @Test
    public void run67() throws Exception {
        Map<String, String> extraMap = new HashMap<>();
        extraMap.put("abc", "111");
        extraMap.put("abc2", "d1");
        log.info("{}--", MapUtil.toString(extraMap));
    }
}
