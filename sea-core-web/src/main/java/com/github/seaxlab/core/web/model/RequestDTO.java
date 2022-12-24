package com.github.seaxlab.core.web.model;

import lombok.*;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/3
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RequestDTO implements Serializable {

  private String uri;
  private String method;
  private String contentType;
  private String queryString;
  private int contentLength;
  private String userAgent;
}
