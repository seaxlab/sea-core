package com.github.spy.sea.core.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/23
 * @since 1.0
 */
@Slf4j
public class UserServiceImpl extends UserService<User2> {
    public User2 get(Long id) {
        User2 user = new User2();
        user.setId(id);
        user.setXx("xx");
        return user;
    }
}
