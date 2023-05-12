package com.example.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*@Configuration
public class SecuredFilterConfig {
    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {

       FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/profile/private/*");
        bean.addUrlPatterns("/article/private/*");
        bean.addUrlPatterns("/articleType/private/*");
        bean.addUrlPatterns("/category/private/*");
        bean.addUrlPatterns("/region/private/*");
        bean.addUrlPatterns("/tag/private/*");
        return bean;

    }
}*/
