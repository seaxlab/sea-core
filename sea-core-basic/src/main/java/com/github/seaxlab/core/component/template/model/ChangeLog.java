package com.github.seaxlab.core.component.template.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * change log
 *
 * @author spy
 * @version 1.0 2023/11/17
 * @since 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ChangeLog<T extends BaseLog> {

  private String traceId;

  private T before;
  private T after;
}
