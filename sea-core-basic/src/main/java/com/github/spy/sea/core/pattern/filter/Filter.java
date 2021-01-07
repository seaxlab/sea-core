package com.github.spy.sea.core.pattern.filter;


/**
 * filter pattern
 * You should use real class type replace of <code> Request,Response </code> in action.
 * warn: no order feature.
 *
 * @author spy
 * @version 1.0 2021/1/7
 * @since 1.0
 */
public interface Filter<Request, Response> {

    /**
     * rpc filter
     *
     * @param request
     * @param filterChain
     * @return
     * @throws Exception
     */
    Response doFilter(Request request, FilterChain filterChain) throws Exception;
}
