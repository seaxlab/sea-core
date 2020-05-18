package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSON;
import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.util.model.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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


        Map<String, List<Teacher>> map = new HashMap<>();
//        map.put("key", 11);
        map.put("teachers", list);

        // ok
        Map newMap = MapUtil.clone(map);
        log.info("{}", newMap);

        List<Teacher> list2 = (List<Teacher>) newMap.get("teachers");

        // ERROR
        Teacher t2 = list2.get(0);

        t2.setName("2222");
        log.info("map={}", map);
        log.info("newMap={}", newMap);
    }

    @Test
    public void fastJSONTest() throws Exception {

        List<Teacher> list = new ArrayList<>();
        Teacher t = new Teacher();
        t.setName("smith");
        list.add(t);


        Map<String, Object> map = new HashMap<>();
        map.put("key", 11);
        map.put("teachers", list);

        log.info("{}", JSON.toJSONString(map.toString()));
        Map newMap = JSON.parseObject(JSON.toJSONString(map.toString()), Map.class);
        log.info("{}", newMap);
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

    @Test
    public void run75() throws Exception {

        Map<String, String> cache = new HashMap<>(4);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3"); // 不扩容
        cache.put("4", "4"); // 扩容

        cache = MapUtil.newNoResizeHashMap(4);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3"); // 不扩容
        cache.put("4", "4"); // 不扩容
    }
}
