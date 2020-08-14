package cn.minalz.controller;

import cn.minalz.dao.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository scmciwhUserRepository;

    @GetMapping("/login")
    public String loginPage() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            return "redirect:/login_success";
        }
        return "login.html";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        // <1> 判断是否已经登陆
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            return "你已经登陆账号：" + subject.getPrincipal();
        }

        // <2> 获得登陆失败的原因
        String shiroLoginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        // 翻译成人类看的懂的提示
        String msg = "";
        if (UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
            msg = "账号不存在";
        } else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
            msg = "密码不正确";
        } else if (LockedAccountException.class.getName().equals(shiroLoginFailure)) {
            msg = "账号被锁定";
        } else if (ExpiredCredentialsException.class.getName().equals(shiroLoginFailure)) {
            msg = "账号已过期";
        } else {
            msg = "未知";
            logger.error("[login][未知登陆错误：{}]", shiroLoginFailure);
        }
        return "登陆失败，原因：" + msg;
        /*try{
            // subject - securityManager - realm
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(username,password);
            subject.login(token);
            return "success";
        }catch (UnknownAccountException e){
            e.printStackTrace();
            model.addAttribute("errMessage","用户不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            model.addAttribute("errMessage","密码错误");
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errMessage","系统错误");
            return "login";
        }*/
    }

    @RequestMapping("/user/logout")
//    @ResponseBody
    public String logout(){
        System.out.println("执行退出登录的操作");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    @GetMapping("/login_success")
//    @ResponseBody
    public String loginSuccess() {
        return "index.html";
    }

    @GetMapping("/unauthorized")
//    @ResponseBody
    public String unauthorized() {
        return "unauthorized.html";
    }

}