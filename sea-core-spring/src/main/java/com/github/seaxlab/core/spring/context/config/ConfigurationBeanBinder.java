package com.github.seaxlab.core.spring.context.config;

import com.github.seaxlab.core.spring.context.annotation.EnableConfigurationBeanBinding;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * The binder for the configuration bean
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.3
 */
public interface ConfigurationBeanBinder {

  /**
   * Bind the properties in the {@link Environment} to Configuration bean under specified prefix.
   *
   * @param configurationProperties The configuration properties
   * @param ignoreUnknownFields     whether to ignore unknown fields, the value is come
   *                                from the attribute of {@link EnableConfigurationBeanBinding#ignoreUnknownFields()}
   * @param ignoreInvalidFields     whether to ignore invalid fields, the value is come
   *                                from the attribute of {@link EnableConfigurationBeanBinding#ignoreInvalidFields()}
   * @param configurationBean       the bean of configuration
   */
  void bind(Map<String, Object> configurationProperties, boolean ignoreUnknownFields, boolean ignoreInvalidFields,
            Object configurationBean);
}
