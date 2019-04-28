package com.gui.star.web.config;

import com.gui.star.web.interceptors.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类(实现WebMvcConfigurer接口)
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 注册自定义的拦截器类
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 加入自定义拦截器，这里可以根据实际需要对各个url添加不同的拦截器
        registry.addInterceptor(logInterceptor).addPathPatterns("/**/**");
    }
}
