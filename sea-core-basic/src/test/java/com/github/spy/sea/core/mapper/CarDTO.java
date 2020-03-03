package com.github.spy.sea.core.mapper;

import lombok.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarDTO {

    private String carName;
    private String make;
    private int seatCount;
    private String type;


}
