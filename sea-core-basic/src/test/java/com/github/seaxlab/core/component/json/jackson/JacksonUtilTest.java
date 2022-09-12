package com.github.seaxlab.core.component.json.jackson;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/12
 * @since 1.0
 */
@Slf4j
public class JacksonUtilTest extends BaseCoreTest {
    @Test
    public void testToString() throws Exception {
        User user = new User();
        user.setName("1");
        user.setCreateTime(new Date());
        log.info("{}", JacksonUtil.toString(user));
    }

    @Test
    public void testToObject() throws Exception {
        String json = "{\"name\":\"1\",\"createTime\":1662987075464}";
        User user = JacksonUtil.toObject(json, User.class);
        log.info("{}", user);
    }

    @Test
    public void testToList() throws Exception {
        String json = "[{\"name\":\"1\",\"createTime\":1662987075464},{\"name\":\"2\",\"createTime\":1662987075464}]";
        List<User> users = JacksonUtil.toList(json, User.class);
        log.info("{}", users);
    }

    @Test
    public void testToMapList() throws Exception {
        String json = "[{\"name\":\"1\",\"createTime\":1662987075464},{\"name\":\"2\",\"createTime\":1662987075464}]";
        List<Map<String, Object>> users = JacksonUtil.toMapList(json);
        log.info("{}", users);
    }

    @Test
    public void testBeansToMaps() throws Exception {
        List<User> users = new ArrayList<User>();

        User user = new User();
        user.setName("1");
        user.setCreateTime(new Date());
        users.add(user);

        user = new User();
        user.setName("2");
        user.setCreateTime(new Date());
        users.add(user);
        log.info("{}", JacksonUtil.beansToMaps(users));
    }

    @Test
    public void testMapsToBeans() throws Exception {
        List<Map<String, Object>> users = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "11");
        map.put("remark", "11");
        users.add(map);

        map = new HashMap<>();
        map.put("name", "22");
        map.put("remark", "221");
        users.add(map);

        List<User> userList = JacksonUtil.mapsToBeans(users, User.class);
        log.info("user list={}", userList);
    }

}
