package com.github.spy.sea.core.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 指定名称的thread
 *
 * @author spy
 * @version 1.0 2019-06-11
 * @since 1.0
 */
@Slf4j
public class NamedThread extends Thread {

    private String name;

    private NamedThread() {

    }

    public NamedThread(String name) {
        super(name);
        this.name = name;
    }
}
