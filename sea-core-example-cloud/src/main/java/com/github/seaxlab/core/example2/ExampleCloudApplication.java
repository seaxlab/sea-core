package com.github.seaxlab.core.example2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * main entry
 *
 * @author spy
 * @version 1.0 2022/10/31
 * @since 1.0
 */
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients("com.github.seaxlab.core.example2.feign")
@SpringBootApplication(scanBasePackages = {"com.github.seaxlab.core.example2"})
public class ExampleCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExampleCloudApplication.class, args);
  }
}
