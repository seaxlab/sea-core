package com.github.spy.sea.core.mapper;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */
@Slf4j
public class CarMapperTest extends BaseCoreTest {

    @Test
    public void shouldMapCarToDto() {
        //given
        Car car = new Car();
        car.setId("1");
        car.setName("audi A4L");
        car.setMake("china");
        car.setNumberOfSeats(5);

        //when
        CarDTO carDto = CarMapper.INSTANCE.carToCarDto(car);

        //then
        log.info("car={}", car);
        log.info("carDto={}", carDto);
    }
}
