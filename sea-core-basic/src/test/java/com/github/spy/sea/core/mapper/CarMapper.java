package com.github.spy.sea.core.mapper;

import com.github.spy.sea.core.util.ListUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

/**
 * 特别注意lombok与mapstruct版本
 * lombok1.16.22 与mapstruct 1.4.1.final可以正常运作
 * lombok1.18.16 与mapstruct 1.4.1.final一起有问题
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */
// 这里无论是jdk的类还是业务系统中，都需要import进来
@Mapper(imports = {ListUtil.class, Collections.class})
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "numberOfSeats", target = "seatCount")
    @Mapping(source = "name", target = "carName")
    @Mapping(source = "names", target = "names", defaultExpression = "java(ListUtil.empty())")
    @Mapping(source = "wheels", target = "wheels", defaultExpression = "java(Collections.emptyList())")
    CarDTO carToCarDto(Car car);
}

// sea-core/sea-core-basic/target/generated-test-sources/test-annotations/com/github/spy/sea/core/mapper/CarMapperImpl.java
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