package cn.minalz.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义异常处理器
 */
@ControllerAdvice
public class GloableExceptionResolver {

    @ExceptionHandler(UnauthorizedException.class)
    public void calUnauthorizedException(UnauthorizedException e){
        PrintWriter writer = null;
        try{
            //判断是否是ajax
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpServletResponse response = requestAttributes.getResponse();
            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNoneBlank(header) && "XMLHttpRequest".equalsIgnoreCase(header)){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                writer = response.getWriter();
//                {"status":401,"message":"无权访问"}
//                String respStr = ""
                writer.write("{\"status\":401,\"message\":\"无权访问\"}");
            }else{
                String contextPath = request.getContextPath();
                if("/".equals(contextPath))
                    contextPath = "";
                response.sendRedirect(request.getContextPath() + "/unauthorized");
            }
        }catch (IOException io){
            io.printStackTrace();
        }finally {
            if(writer != null)
                writer.close();
        }
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public void calUnauthorizedException(UnauthenticatedException e){
        PrintWriter writer = null;
        try{
            //判断是否是异步请求
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpServletResponse response = requestAttributes.getResponse();
            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNoneBlank(header) && "XMLHttpRequest".equalsIgnoreCase(header)){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                writer = response.getWriter();
//                {"status":401,"message":"无权访问"}
//                String respStr = ""
                writer.write("{\"status\":302,\"message\":\"请前去登录\"}");
            }else{
                String contextPath = request.getContextPath();
                if("/".equals(contextPath))
                    contextPath = "";
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }catch (IOException io){
            io.printStackTrace();
        }finally {
            if(writer != null)
                writer.close();
        }
    }

}
