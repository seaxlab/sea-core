package com.github.seaxlab.core.spring;

import com.github.seaxlab.core.spring.component.extension.register.ExtensionScanner;
import com.github.seaxlab.core.spring.component.extension.executor.ExtensionExecutor;
import com.github.seaxlab.core.spring.component.extension.register.ExtensionRegister;
import com.github.seaxlab.core.spring.component.extension.register.ExtensionRepository;
import com.github.seaxlab.core.spring.context.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

/**
 * TestSpringConfig
 *
 * @author Frank Zhang
 * @date 2020-06-18 8:03 PM
 */
@Configuration
@ComponentScan(value = {"com.github.seaxlab.core.spring"},
  excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.github.seaxlab.core.spring.context.*")})
@PropertySource(value = {"classpath:sample.properties"})
public class TestSpringConfig {

  /**
   * 必须在bootstrap初始化之前
   *
   * @return
   */
  @Bean
  public SpringContextHolder springContextHolder() {
    return new SpringContextHolder();
  }

//    @Bean(initMethod = "init")
//    public ExtensionBootstrap bootstrap() {
//        ExtensionBootstrap bootstrap = new ExtensionBootstrap();
//        return bootstrap;
//    }


  /**
   * 扩展点缓存
   * @return
   */
  @Bean("seaCoreExtensionRepository")
  public ExtensionRepository repository() {
    return new ExtensionRepository();
  }

  /**
   * 扩展点注册器
   * @return
   */
  @Bean("seaCoreExtensionRegister")
  public ExtensionRegister register() {
    return new ExtensionRegister();
  }

  /**
   * 扩展点启动
   * @return
   */
  @Bean("seaCoreExtensionBootstrap")
  public ExtensionScanner bootstrap() {
    return new ExtensionScanner();
  }

  /**
   * 扩展点业务使用执行器
   * @return
   */
  @Bean("seaCoreExtensionExecutor")
  public ExtensionExecutor executor() {
    return new ExtensionExecutor();
  }

}
