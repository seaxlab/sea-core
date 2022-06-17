package com.github.seaxlab.core.pattern.filter;


import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/7
 * @since 1.0
 */
public class FilterChain<Request, Response> {

    private final int index;

    private final List<Filter> filters;
    private final Delegate delegate;

    public FilterChain(List<Filter> filters, Delegate delegate) {
        this.filters = filters;
        this.delegate = delegate;
        this.index = 0;
    }

    private FilterChain(FilterChain parent, int index) {
        this.filters = parent.getFilters();
        this.delegate = parent.getDelegate();
        this.index = index;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public Delegate getDelegate() {
        return delegate;
    }

    public Response doFilter(Request request) throws Exception {
        if (this.index < filters.size()) {
            Filter filter = filters.get(this.index);
            FilterChain chain = new FilterChain(this, this.index + 1);
            return (Response) filter.doFilter(request, chain);
        } else {
            return (Response) delegate.invoke(request);
        }
    }

    public interface Delegate<Request, Response> {
        /**
         * invoke
         *
         * @param request
         * @return
         * @throws Exception
         */
        Response invoke(Request request) throws Exception;
    }
}
