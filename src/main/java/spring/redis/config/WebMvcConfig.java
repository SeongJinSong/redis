package spring.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spring.redis.util.AccessLimit;
import spring.redis.util.AccessLimitInterceptor;

public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public AccessLimitInterceptor accessLimitInterceptor(){
        return new AccessLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AccessLimitInterceptor accessLimitInterceptor = new AccessLimitInterceptor();
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/login.html","/user/login");
        //API
        registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**","/login.html");
    }
}
