package com.gui.star.web.config;

import com.gui.star.web.filter.CustomFilter;
import com.gui.star.web.filter.CustomFollowFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器第二种使用方式配置
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        CustomFilter customFilter = new CustomFilter();
        registrationBean.setFilter(customFilter);
        registrationBean.setOrder(10);//设置过滤器执行的先后顺序

        List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        CustomFollowFilter customFollowFilter = new CustomFollowFilter();
        registrationBean.setFilter(customFollowFilter);
        registrationBean.setOrder(1);

        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }
}
