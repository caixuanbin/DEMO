package com.xbcai.myweb.configuration;

import com.xbcai.myweb.filter.GConfigFilter;
import com.xbcai.myweb.filter.GFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfiguration {
    @Bean
    FilterRegistrationBean globalFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new GConfigFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
