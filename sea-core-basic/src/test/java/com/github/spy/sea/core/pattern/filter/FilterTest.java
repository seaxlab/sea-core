package com.github.spy.sea.core.pattern.filter;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/7
 * @since 1.0
 */
@Slf4j
public class FilterTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        List<Filter> filters = new ArrayList<>();
        Filter filter = (request, filterChain) -> {
            log.info("filter1 begin={}", request);

            String newRequest = request + ".1";
            String ret = (String) filterChain.doFilter(newRequest);

            log.info("filter1 end");

            return ret;
        };
        filters.add(filter);

        Filter<String, String> filter2 = (Filter) (request, filterChain) -> {
            log.info("filter2 begin={}", request);
            String newRequest = request + ".2";
            String ret = (String) filterChain.doFilter(newRequest);
            log.info("filter2 end");
            return ret;

        };
        filters.add(filter2);

        FilterChain<String, String> filterChain = new FilterChain<>(filters, request -> {
            log.info("final request={}", request);
            String newRequest = request + ".final";

            return "" + newRequest;
        });

        String ret = filterChain.doFilter("abc");

        log.info("ret={}", ret);
    }
}
