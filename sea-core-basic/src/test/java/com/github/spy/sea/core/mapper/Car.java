package com.github.spy.sea.core.mapper;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {

    private String make;
    private String name;
    private int numberOfSeats;
//    private CarType type;

    //constructor, getters, setters etc.


}
