package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author : Yang
 * @Date :  2023/3/3 15:00
 * @Description：
 */
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(loginInterceptor())
               //拦截的路径
            //   .addPathPatterns("/api/coupon/*/**","/api/coupon_record/*/**")

               //排查不拦截的路径
               .excludePathPatterns("/api/coupon/*/pageCoupon");
    }
}
