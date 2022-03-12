package com.github.spy.sea.core.example.controller;

import com.github.spy.sea.core.example.service.UserService;
import com.github.spy.sea.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/16
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Qualifier("user1Service")
    private UserService user1Service;

    @Autowired
    @Qualifier("user2Service")
    private UserService user2Service;

    @GetMapping("/1")
    public Result queryUser1() {
        return Result.success(user1Service.queryName());
    }

    @GetMapping("/2")
    public Result queryUser2() {
        return Result.success(user2Service.queryName());
    }

    @GetMapping("/3")
    public Result queryUser3() {
        return Result.success(user1Service.queryName2());
    }

}
