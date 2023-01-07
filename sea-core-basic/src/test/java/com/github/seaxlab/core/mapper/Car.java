package com.github.seaxlab.core.mapper;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {
  private String id;
  private String make;
  private String name;
  private int numberOfSeats;
  private List<String> names;
  private List<String> wheels;

//    private CarType type;

  //constructor, getters, setters etc.


}
