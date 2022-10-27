package com.github.seaxlab.core.boot.autoconfigure.config;

import com.github.seaxlab.core.boot.autoconfigure.SeaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * sea core config
 *
 * @author spy
 * @version 1.0 2021/2/6
 * @since 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SeaCoreConfig {

    private final SeaProperties seaProperties;


    //private static final String DEFAULT_EXPRESSION_PROFILER = "@annotation(com.github.seaxlab.core.component.perf.anno.Profiler)";

}
