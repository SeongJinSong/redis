package spring.redis.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

@Slf4j
public class RequestUtils {
    public static void out(ServletResponse response, String message){
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JSONObject.fromObject(message).toString());
        } catch (Exception e) {
            log.error("  JSON  !"+e);
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
