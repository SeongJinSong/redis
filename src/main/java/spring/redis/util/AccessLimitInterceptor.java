package spring.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AccessLimitInterceptor.preHandle");
        try{
            // Handler HandlerMethod
            if(handler instanceof HandlerMethod){
                //
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                //
                Method method = handlerMethod.getMethod();
                // AccessLimit
                if(!method.isAnnotationPresent(AccessLimit.class)){
                    return true;
                }
                //
                AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
                if(accessLimit == null){
                    return true;
                }
                int times = accessLimit.times();//
                int second = accessLimit.second();//
                // IP + API
                String key = IpUtils.getIpAddr(request) + request.getRequestURI();
                // key
                Integer maxTimes = redisTemplate.opsForValue().get(key);
                if(maxTimes == null){
                    // set
                    redisTemplate.opsForValue().set(key, 1, second, TimeUnit.SECONDS);
                }else if(maxTimes < times){
                    redisTemplate.opsForValue().set(key, maxTimes+1, second, TimeUnit.SECONDS);
                }else{
                    // 30405 API_REQUEST_TOO_MUCH
                    RequestUtils.out(response, "API_REQUEST_TOO_MUCH");
                    return false;
                }
            }
        }catch (Exception e){
            log.error("API, Redis!",e);
            throw e;
        }
        return true;
    }
}
