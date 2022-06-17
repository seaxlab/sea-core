package com.github.seaxlab.core.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-15
 * @since 1.0
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class BaseSpringTest {
}
