package com.gui.star.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 利用WebFilter注解配置，启动类要结合@ServletComponentScan注解
 */
@WebFilter(filterName = "acustomFollowFilter1", urlPatterns = "/*")
@Slf4j
public class AcustomFollowFilter1 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("AcustomFollowFilter1初始化...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter in AcustomFollowFilter1请求处理....");

        //对request、response进行一些预处理，比如设置请求编码
        // request.setCharacterEncoding("UTF-8");
        // response.setCharacterEncoding("UTF-8");

        //TODO 进行业务逻辑

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("AcustomFollowFilter1销毁...");
    }
}
