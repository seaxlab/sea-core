package com.github.seaxlab.core.spring.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 * The utilities class for wrapper interfaces
 *
 * @author x
 * @since 1.0.6
 */
public abstract class WrapperUtil {

  /**
   * Unwrap {@link BeanFactory} to {@link ConfigurableListableBeanFactory}
   *
   * @param beanFactory {@link ConfigurableListableBeanFactory}
   * @return {@link ConfigurableListableBeanFactory}
   * @throws IllegalArgumentException If <code>beanFactory</code> argument is not an instance of {@link ConfigurableListableBeanFactory}
   */
  public static ConfigurableListableBeanFactory unwrap(BeanFactory beanFactory) throws IllegalArgumentException {
    Assert.isInstanceOf(ConfigurableListableBeanFactory.class, beanFactory,
      "The 'beanFactory' argument is not an instance of ConfigurableListableBeanFactory, " +
        "is it running in Spring container?");
    return ConfigurableListableBeanFactory.class.cast(beanFactory);
  }


  /**
   * Unwrap {@link Environment} to {@link ConfigurableEnvironment}
   *
   * @param environment {@link Environment}
   * @return {@link ConfigurableEnvironment}
   * @throws IllegalArgumentException If <code>environment</code> argument is not an instance of {@link ConfigurableEnvironment}
   */
  public static ConfigurableEnvironment unwrap(Environment environment) throws IllegalArgumentException {
    Assert.isInstanceOf(ConfigurableEnvironment.class, environment,
      "The 'environment' argument is not a instance of ConfigurableEnvironment, " +
        "is it running in Spring container?");
    return (ConfigurableEnvironment) environment;
  }
}
