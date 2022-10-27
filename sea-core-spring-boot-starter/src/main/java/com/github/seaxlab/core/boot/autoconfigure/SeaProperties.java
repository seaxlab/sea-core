package com.github.seaxlab.core.boot.autoconfigure;

import com.github.seaxlab.core.common.Env;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * yml配置文件
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

    /**
     * region, eg: jiangsu-idc
     */
    private String region;

    /**
     * 当前环境
     *
     * @see Env
     */
    private String env;

    /**
     * profile for sub env.
     */
    private String profile;

    /**
     * application name. if null, it will be spring.application.name.
     */
    @Value("${spring.application.name:sea-default-app}")
    private String appName;

    /**
     * base package for all custom, can split by comma
     * <ul>
     * <li>aop</li>
     * </ul>
     */
    private String basePackage;

    /**
     * 是否需要开启@scheduled注解清理上下文，默认false
     */
    public Boolean scheduleThreadContextEnable = false;
    /**
     * Scheduled注解需要清理thread context包路径，需符合aspectj pointcut expression规范
     */
    private String scheduleThreadContextBasePackage;

//    @NestedConfigurationProperty
//    private Fastjson fastjson;
//
//    @Data
//    public static class Fastjson {
//        private Boolean enable = false;
//        //输出空值
//        private Boolean writeNullValue;
//    }
//
//    @NestedConfigurationProperty
//    private Redis redis;
//
//    @Data
//    public static class Redis {
//        private String host;
//        private int port;
//        private String password;
//        private int database;
//    }

}
