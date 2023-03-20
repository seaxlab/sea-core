package com.github.seaxlab.core.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @since 1.0
 */
@Slf4j
@EnableScheduling
@SpringBootApplication(
  scanBasePackages = {"com.github.seaxlab.core.example", "com.abc", "com.github.seaxlab.core.spring.controller"}
)
public class ExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }

}


