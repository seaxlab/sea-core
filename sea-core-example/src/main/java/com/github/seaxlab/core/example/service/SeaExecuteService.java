package com.github.seaxlab.core.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sea execute service
 *
 * @author spy
 * @version 1.0 2023/10/9
 * @since 1.0
 */
@Service
@Slf4j
public class SeaExecuteService {

  public String add(String a, String b) {
    return a + b;
  }

}
