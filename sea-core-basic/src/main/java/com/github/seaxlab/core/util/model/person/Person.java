package com.github.seaxlab.core.util.model.person;

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
public class Person {

  private Name name;
  private Age age;
  private List<Address> addressList;
}
