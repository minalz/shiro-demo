package cn.minalz.config.filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 自定义authc权限验证的过滤器
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        // 登录有一个问题 就是当session过期了 会根据之前旧的浏览器地址进行跳转 不太友好
        // 处理：直接跳转到登录页
        this.redirectToLogin(request, response);
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception{
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            // option请求处理
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
                resp.setStatus(HttpStatus.OK.value());
                return true;
            }
            // 重定向到login页面
            this.redirectToLogin(request, response);
//            WebUtils.issueRedirect(request, response, loginUrl);
            // 取消重定向，直接返回结果
//            returnTokenInvalid((HttpServletRequest)request, (HttpServletResponse)response);
            return false;
        }
    }

    /**
     * 替代shiro重定向
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void returnTokenInvalid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream()));
        out.write("无权限访问");
//        out.write(JSONObject.toJSONString(new Result(ResultStatusCode.INVALID_TOKEN)));
        out.flush();
        out.close();
    }
}
