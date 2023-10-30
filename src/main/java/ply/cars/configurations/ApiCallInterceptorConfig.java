package ply.cars.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ply.cars.interceptors.ApiCallInterceptor;

@Configuration
public class ApiCallInterceptorConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new ApiCallInterceptor());
    }
    
}
