package com.github.seaxlab.core.spring.context.config;

import com.github.seaxlab.core.spring.context.annotation.ConfigurationBeanBindingPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

/**
 * The customizer for the configuration bean after {@link ConfigurationBeanBinder#bind its binding}.
 * <p>
 * If There are multiple {@link ConfigurationBeanCustomizer} beans in the Spring {@link ApplicationContext context},
 * they are executed orderly, thus the subclass should be aware to implement the {@link #getOrder()} method.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see ConfigurationBeanBinder
 * @see ConfigurationBeanBindingPostProcessor
 * @since 1.0.3
 */
public interface ConfigurationBeanCustomizer extends Ordered {

  /**
   * Customize the configuration bean
   *
   * @param beanName          the name of the configuration bean
   * @param configurationBean the configuration bean
   */
  void customize(String beanName, Object configurationBean);

}
