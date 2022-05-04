package spring.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spring.redis.util.AccessLimitInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public AccessLimitInterceptor accessLimitInterceptor(){
        return new AccessLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/login.html","/user/login");
        //API
        registry.addInterceptor(accessLimitInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/login.html");
    }
}
