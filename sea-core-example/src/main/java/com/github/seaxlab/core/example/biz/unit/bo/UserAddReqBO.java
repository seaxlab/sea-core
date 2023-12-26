package com.github.seaxlab.core.example.biz.unit.bo;

import lombok.Data;

import java.util.List;

/**
 * user add Request BO
 *
 * @author spy
 * @version 1.0 2023/07/24
 * @since 1.0
 */
@Data
public class UserAddReqBO {

  private String name;
  private int age;
  private List<Integer> levels;

}
