package com.github.seaxlab.core.example.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.example.util.HttpUtils;
import com.github.seaxlab.core.example.util.SignUtil;
import com.github.seaxlab.core.web.servlet.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/4/28
 * @since 1.0
 */
@Slf4j
public class SignAuthFilter implements Filter {

  private static final String FAVICON = "/favicon.ico";

  @Override
  public void init(FilterConfig filterConfig) {
    log.info("init SignAuthFilter");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    // 防止流读取一次后就没有了, 所以需要将流继续写出去
    HttpServletRequest request = (HttpServletRequest) req;
    // TODO 重点
    HttpServletRequest wrapperRequest = new BodyReaderHttpServletRequestWrapper(request);
    //获取图标不需要验证签名
    if (FAVICON.equals(wrapperRequest.getRequestURI())) {
      chain.doFilter(request, response);
    } else {
      //获取全部参数(包括URL和body上的)
      SortedMap<String, String> allParams = HttpUtils.getAllParams(wrapperRequest);
      //对参数进行签名验证
      boolean isSigned = SignUtil.verifySign(allParams);
      if (isSigned) {
        log.info("签名通过");
        // TODO 重点
        chain.doFilter(wrapperRequest, response);
      } else {
        log.info("参数校验出错");
        //校验失败返回前端
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject resParam = new JSONObject();
        resParam.put("msg", "参数校验出错");
        resParam.put("success", "false");
        out.append(resParam.toJSONString());
      }
    }

  }
}