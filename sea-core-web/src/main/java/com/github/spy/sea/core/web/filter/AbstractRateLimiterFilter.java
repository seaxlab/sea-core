package com.github.spy.sea.core.web.filter;

import com.github.spy.sea.core.annotation.Beta;
import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.util.AntPathMatcher;
import com.github.spy.sea.core.util.PathMatcher;
import com.github.spy.sea.core.web.model.RateLimiterConfig;
import com.github.spy.sea.core.web.util.ResponseUtil;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 限流抽象类，具体由业务系统实现
 * <p>
 * 这个必须在spring 初始化之后再初始化
 * </p>
 *
 * @author spy
 * @version 1.0 2021/12/14
 * @since 1.0
 */
@Slf4j
@Beta
public abstract class AbstractRateLimiterFilter implements Filter {

    private ConcurrentHashMap<String, RateLimiter> cache;

    private PathMatcher pathMatcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pathMatcher = new AntPathMatcher();
        cache = new ConcurrentHashMap<>();
        List<RateLimiterConfig> rateLimiterConfigs = getRateLimiterConfigs();

        for (RateLimiterConfig rateLimiterConfig : rateLimiterConfigs) {
            cache.putIfAbsent(rateLimiterConfig.getUrl(), RateLimiter.create(rateLimiterConfig.getQps()));
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();

        Set<Map.Entry<String, RateLimiter>> entrys = cache.entrySet();
        for (Map.Entry<String, RateLimiter> entry : entrys) {
            if (pathMatcher.match(entry.getKey(), url)) {
                RateLimiter rateLimiter = entry.getValue();
                // go next if it acquires in 5 seconds
                if (rateLimiter.tryAcquire(5, TimeUnit.SECONDS)) {
                    chain.doFilter(req, resp);
                    return;
                } else {
                    log.warn("not get rate limiter token, so end request, url={}", url);
                    Result result = Result.fail();
                    result.setCode(CoreErrorConst.SYS_RATE_LIMITER_ERR);
                    result.setMsg("触发限流");
                    ResponseUtil.toJSON(resp, result);
                    return;
                }
                // should end, if hint url pattern.
            }
        }

        // pass all by default.
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        cache.clear();
    }

    /**
     * 获取维度key，基于sass的软件，会有很多维度，不能将使用一个维度对所有租户进行限制
     *
     * @param request
     * @return
     */
    abstract String getShardingKey(HttpServletRequest request);

    /**
     * 获取限流配置
     *
     * @return
     */
    abstract List<RateLimiterConfig> getRateLimiterConfigs();

}
