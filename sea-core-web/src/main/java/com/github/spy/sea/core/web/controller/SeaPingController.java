package com.github.spy.sea.core.web.controller;

import com.github.spy.sea.core.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Sea Ping
 *
 * @author spy
 * @version 1.0 2020/9/2
 * @since 1.0
 */
@Slf4j
@Controller
public class SeaPingController {

    @RequestMapping("/api/sea/ping")
    public void ping(HttpServletResponse response) {
        ResponseUtil.toText(response, "pong");
    }
}
