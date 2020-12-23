package com.github.spy.sea.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings({
            @Mapping(source = "numberOfSeats", target = "seatCount"),
            @Mapping(source = "name", target = "carName")
    })
    CarDTO carToCarDto(Car car);
}


/**
 * it will be generate xxxImpl, look good.
 * <pre>
 *     package com.github.spy.sea.core.mapper;
 *
 * public class CarMapperImpl implements CarMapper {
 *     public CarMapperImpl() {
 *     }
 *
 *     public CarDTO carToCarDto(Car car) {
 *         if (car == null) {
 *             return null;
 *         } else {
 *             CarDTO carDTO = new CarDTO();
 *             carDTO.setCarName(car.getName());
 *             carDTO.setSeatCount(car.getNumberOfSeats());
 *             carDTO.setMake(car.getMake());
 *             return carDTO;
 *         }
 *     }
 * }
 * </pre>
 */