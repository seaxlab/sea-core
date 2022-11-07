package com.github.seaxlab.core.component.json.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import com.github.seaxlab.core.domain.User;
import com.github.seaxlab.core.enums.IBaseEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/12
 * @since 1.0
 */
@Slf4j
public class JacksonUtilTest extends BaseCoreTest {

    @Data
    public static class Student {

        private String name;
        private StatusEnum status;

        //extend
        public String getStatusDesc() {
            return this.status.getDesc();
        }
    }

    @Getter
    public enum StatusEnum implements IBaseEnum<Integer> {
        NORMAL(1, "正常"), //
        EXCEPTION(2, "");
        private Integer code;
        private String desc;

        StatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public static StatusEnum of(int code) {
            for (StatusEnum item : values()) {
                if (item.getCode() == code) {
                    return item;
                }
            }
            return null;
        }

        @JsonValue
        public Integer getCode() {
            return code;
        }

    }

    @Test
    public void testEnum() throws Exception {
        Student student = new Student();
        student.setName("smith");
        student.setStatus(StatusEnum.NORMAL);
        final ObjectMapper objectMapper = new ObjectMapper();
        //SimpleModule simpleModule1 = new SimpleModule();
        //simpleModule1.addSerializer(IBaseEnum.class, new BaseEnumSerializer());
        //simpleModule1.addDeserializer(IBaseEnum.class, new BaseEnumDeserializer());
        //objectMapper.registerModule(simpleModule1);

        log.info("{}", objectMapper.writeValueAsString(student));

        String content = "{\"name\":\"smith\",\"status\":1}";
        Student st = objectMapper.readValue(content, Student.class);
        log.info("{}", st);
    }

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


    @Test
    public void testAlias() throws Exception {
        XY model = new XY();
        model.setName("hello");
        log.info("{}", JacksonUtil.toString(model));
    }

    @Data
    public static class XY {

        @JsonProperty("n")
        private String name;
    }
}
