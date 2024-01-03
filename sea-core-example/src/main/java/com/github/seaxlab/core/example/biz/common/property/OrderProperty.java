package com.github.seaxlab.core.example.biz.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/9/1
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "project.order")
public class OrderProperty {

  private Integer time;

}
