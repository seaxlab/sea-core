package com.github.spy.sea.core.config;

import com.github.spy.sea.core.loader.EnhancedServiceLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数配置工厂
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
public class ConfigurationFactory {


    private static volatile Configuration CONFIG_INSTANCE;

    private ConfigurationFactory() {
    }

    public static Configuration getInstance() {
        if (CONFIG_INSTANCE == null) {
            synchronized (ConfigurationFactory.class) {
                if (CONFIG_INSTANCE == null) {
                    CONFIG_INSTANCE = buildConfiguration();
                }
            }
        }

        return CONFIG_INSTANCE;
    }

    private static Configuration buildConfiguration() {

        //TODO 此时配置文件还未加载

        Configuration configuration = EnhancedServiceLoader.load(Configuration.class, "typesafe");

        return configuration;
//        return new DefaultConfiguration();
    }


}
