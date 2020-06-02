package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/6/2
 * @since 1.0
 */
@Slf4j
public class ExampleUtilTest {

    @Test
    public void run16() throws Exception {
        User user = new User();
        user.setInt1(1);
        user.setIntObj(2);
        user.setLong1(3);
        user.setLongObj(4L);
        user.setBoolean1(true);
        user.setBooleanObj(false);
        user.setUsername("smith");

        List<Field> fieldList = ReflectUtil.getAllFieldsList(user.getClass());

        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);

            if (EqualUtil.isEq("serialVersionUID", field.getName())) {
                continue;
            }


            switch (field.getType().getName()) {
                case "int":
                case "long":
                case "boolean":
                case "java.lang.Integer":
                case "java.lang.Long":
                case "java.lang.Double":
                case "java.lang.Boolean":
                case "java.lang.String":
                    log.info("field={},type={},value={}", field.getName(), field.getType().getName(), ReflectUtil.read(user, field.getName()));
                    break;
                default:
                    log.info("unsupported field={},type={},value={}", field.getName(), field.getType().getName(), ReflectUtil.read(user, field.getName()));
                    break;
            }
        }

    }


}
