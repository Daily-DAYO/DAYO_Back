package com.seoultech.dayo.config;

import com.seoultech.dayo.config.jwt.JwtFilter;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<Filter> logFilter() {

    FilterRegistrationBean<Filter> filterRegistrationBean = new
        FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new JwtFilter());
    filterRegistrationBean.setOrder(1);
    filterRegistrationBean.addUrlPatterns("/*");

    return filterRegistrationBean;
  }

}
