package com.github.seaxlab.core.model.component.person;

import com.github.seaxlab.core.model.layer.dto.DTO;
import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/9
 * @since 1.0
 */
@Data
public class Person extends DTO {

  private Name name;
  private Age age;
  private List<Address> addressList;
}
