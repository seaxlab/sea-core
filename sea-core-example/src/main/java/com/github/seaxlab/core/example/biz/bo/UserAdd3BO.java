package com.github.seaxlab.core.example.biz.bo;

import lombok.Data;

import java.util.List;

/**
 * user add1 BO
 *
 * @author spy
 * @version 1.0 2023/07/13
 * @since 1.0
 */
@Data
public class UserAdd3BO {

  private String name;
  private int age;
  private List<Integer> levels;

}
