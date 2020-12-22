package com.xiaole.shopping.config;

import com.xiaole.shopping.filter.Userfilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration //相当于spring的xml配置
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new Userfilter());
        filterFilterRegistrationBean.addUrlPatterns("/cart/*","/orders/*");//没有登录，过滤以下url
        return  filterFilterRegistrationBean;
    }
}
