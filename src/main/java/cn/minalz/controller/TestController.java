package cn.minalz.controller;

import cn.minalz.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scmciwh")
public class TestController {

    @Autowired
    private UserRepository scmciwhUserRepository;

    @GetMapping("/demo")
    public String demo() {
        return "示例返回";
    }

    @GetMapping("/home")
    public String home() {
        return "我是首页";
    }

    @GetMapping("/admin")
    @ResponseBody
    public Object admin() {
//        List<ScmciwhUserModel> all = scmciwhUserRepository.findAll();
//        return all;
        return "我是管理员";
    }

    @GetMapping("/normal")
    public String normal() {
        return "我是普通用户";
    }

}