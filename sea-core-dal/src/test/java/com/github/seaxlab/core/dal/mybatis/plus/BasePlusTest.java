package com.github.seaxlab.core.dal.mybatis.plus;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.github.seaxlab.core.dal.mybatis.plus.injector.EnhanceSqlInjector;
import com.github.seaxlab.core.test.AbstractCoreSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
@Slf4j
@ContextConfiguration({"classpath:mybatis/spring.xml", "classpath:mybatis/spring-mybatis-plus.xml",})
@Rollback(false)
public class BasePlusTest extends AbstractCoreSpringTest {

    @Bean
    public ISqlInjector EnhanceSqlInjector() {
        return new EnhanceSqlInjector();
    }

}
