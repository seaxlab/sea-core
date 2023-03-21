package com.github.seaxlab.core.model.layer.dto;

import lombok.Data;

/**
 * base page request DTO
 *
 * @author spy
 * @version 1.0 2023/03/21
 * @since 1.0
 */
@Data
public class BasePageRequestDTO extends BaseRequestDTO {

  private Integer pageNum = 1;
  private Integer pageSize = 10;

}
