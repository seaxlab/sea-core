package com.github.spy.sea.core.sensitive.fastjson;

import com.alibaba.fastjson.JSON;
import com.github.spy.sea.core.component.sensitive.fastjson.core.SensitiveSerializeValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Slf4j
public class FastjsonSensitiveTest {
    @Test
    public void test() {
        UserEntity userEntity = UserEntity.builder()
                                          .userNamePattern("张三四")
                                          .userNameLength("张三四")
                                          .passwordPattern("122345676543")
                                          .passwordLength("122345676543")
                                          .idCardPattern("432145167805126789")
                                          .idCardLength("432145167805126789")
                                          .fixedPhonePattern("076512344321")
                                          .fixedPhoneLength("076512344321")
                                          .mobilePattern("15678900987")
                                          .mobileLength("15678900987")
                                          .addressPattern("北京市东城区东华门街道北京香江戴斯酒店")
                                          .addressLength("北京市东城区东华门街道北京香江戴斯酒店")
                                          .emailPattern("23345@qq.com")
                                          .emailLength("23345@qq.com")
                                          .bankCardPattern("6212262502009182455")
                                          .bankCardCustomizePattern("6212262502009182455")
                                          .bankCardLength("6212262502009182455")
                                          .build();
        log.info(JSON.toJSONString(userEntity, new SensitiveSerializeValueFilter()));
    }

    @Test
    public void test42() throws Exception {
        Field field = UserEntity.class.getDeclaredField("addressPattern");
        field.setAccessible(true);

        log.info("{}", field.getAnnotations());
    }
}
