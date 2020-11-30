package com.github.spy.sea.core.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Properties;

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

    /**
     * store all properties. eg. host field.
     */
    private Properties properties = new Properties();

    public Properties getProperties() {
        return this.properties;
    }

//    public void setHost(String host) {
//        this.properties.setProperty("host", host);
//    }

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
