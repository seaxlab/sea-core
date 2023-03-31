package com.github.seaxlab.core.example.controller.dto.order;

import lombok.Data;
import lombok.ToString;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/3/30
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
public class UserOrderDTO extends BaseOrderDTO {

  private String userName;
}
