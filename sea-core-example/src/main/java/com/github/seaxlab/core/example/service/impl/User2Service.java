package com.github.seaxlab.core.example.service.impl;

import com.github.seaxlab.core.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/16
 * @since 1.0
 */
@Slf4j
@Service("user2Service")
public class User2Service implements UserService {
    @Override
    public String queryName() {
        log.info("user2");
        return "user2";
    }

    @Override
    public String queryName2() {
        log.info("query name2");
        return "query name2";
    }
}
