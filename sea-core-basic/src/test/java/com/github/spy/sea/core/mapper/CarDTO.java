package com.github.spy.sea.core.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarDTO {

    private String carName;
    private String make;
    private int seatCount;
    private String type;


}
