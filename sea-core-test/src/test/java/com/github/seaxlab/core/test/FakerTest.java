package com.github.seaxlab.core.test;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Locale;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/14
 * @since 1.0
 */
@Slf4j
public class FakerTest extends BaseTest {

    @Test
    public void test16() throws Exception {
        Faker faker = new Faker(Locale.CHINA);

        log.info("{}", faker.phoneNumber().cellPhone());
        log.info("{}", faker.name().fullName());
    }
}
