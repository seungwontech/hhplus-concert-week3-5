package com.hhplus.tdd.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/concerts/**");
    }

//    @Bean
//    public FilterRegistrationBean<LoggingFilter> logFilter() {
//        FilterRegistrationBean<LoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new LoggingFilter());
//        filterRegistrationBean.setOrder(1);
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }

}
