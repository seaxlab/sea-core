package com.github.spy.sea.core.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */

@Data
@ConfigurationProperties("sea")
public class SeaProperties {

    @NestedConfigurationProperty
    private Fastjson fastjson;

    @Data
    public static class Fastjson {
        private Boolean enable;
        //输出空值
        private Boolean writeNullValue;
    }

    @NestedConfigurationProperty
    private Redis redis;

    @Data
    public static class Redis {
        private String host;
        private int port;
        private String password;
        private int database;
    }

}
