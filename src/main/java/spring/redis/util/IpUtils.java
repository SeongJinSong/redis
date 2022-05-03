package spring.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("##getIpAddr ip:{}", ip);
        if(StringUtils.hasLength(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //           ip ，   ip    ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.hasLength(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
