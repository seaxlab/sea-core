package com.github.seaxlab.core.boot.autoconfigure;

import com.github.seaxlab.core.spring.component.tunnel.service.HttpTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.StaticTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.impl.DefaultHttpTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.impl.DefaultStaticTunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.impl.DefaultTunnelService;
import com.github.seaxlab.core.spring.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * SeaCore 自动注册
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
@Configuration
@ComponentScan
@ComponentScan({"com.github.seaxlab.core.spring.controller",
  "com.github.seaxlab.core.spring.component.tunnel.controller"})
@EnableConfigurationProperties(SeaProperties.class)
public class SeaCoreAutoConfigure {

  @Resource
  private ApplicationContext applicationContext;

  @Resource
  private TransactionTemplate transactionTemplate;

  /**
   * create spring context holder
   * <p>
   * if you want to create early, you should use @dependOn("springContextHolder") such as: mq listener, it should be
   * after spring context holder init.
   * </p>
   * 重点：请勿更改方法名，这是唯一bean id
   *
   * @return spring context holder bean
   */
  @Bean
  @ConditionalOnMissingBean
  public SpringContextHolder springContextHolder() {
    return new SpringContextHolder();
  }

  // a good choice.
//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        System.out.println("hello world, I have just started up");
//    }

  @Bean("frmTunnelService")
  @ConditionalOnMissingBean(TunnelService.class)
  public TunnelService tunnelService() {
    return new DefaultTunnelService(applicationContext, transactionTemplate);
  }

  @Bean("frmHttpTunnelService")
  @ConditionalOnMissingBean(HttpTunnelService.class)
  public HttpTunnelService httpTunnelService() {
    return new DefaultHttpTunnelService();
  }

  @Bean("frmStaticTunnelService")
  @ConditionalOnMissingBean(StaticTunnelService.class)
  public StaticTunnelService staticTunnelService() {
    return new DefaultStaticTunnelService();
  }

}
