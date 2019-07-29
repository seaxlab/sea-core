package com.github.spy.sea.core.mybatis.handler;

import java.io.Serializable;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */

public class User implements Serializable {

    public Long id;

    public String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
