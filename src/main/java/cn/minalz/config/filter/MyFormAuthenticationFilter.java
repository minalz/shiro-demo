package cn.minalz.config.filter;

import cn.minalz.model.Role;
import cn.minalz.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
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
import java.util.Map;
import java.util.Set;

/**
 * 自定义authc权限验证的过滤器
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        // 登录有一个问题 就是当session过期了 会根据之前旧的浏览器地址进行跳转 不太友好
        // 处理：直接跳转到登录页
        // 最好的方式 应该是判断登录后的用户 是否有这个页面的访问权限，如果没有，那么直接跳转到登录页
        // 如果有的话，应该直接往这个就得登录页进行跳转
        // 现在存在的问题是，如果切换了用户，而之前的url，又是现用户无权限访问的，那么就会出现页面显示不友好的情况
        String successUrl = null;
        boolean contextRelative = true;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
            successUrl = savedRequest.getRequestUrl();
            contextRelative = false;
        }

        User user = (User) subject.getPrincipal();
        Set<Role> roles = user.getRoles();
        roles.forEach(x -> {
            x.getPermissions();
        });

        /*String successUrl = null;
        boolean contextRelative = true;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
            successUrl = savedRequest.getRequestUrl();
            contextRelative = false;
        }

        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the successUrlFallback method parameter. One of these must be non-null for issueSuccessRedirect() to work.");
        } else {
            WebUtils.issueRedirect(request, response, successUrl, (Map)null, contextRelative);
        }*/


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
