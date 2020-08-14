package cn.minalz.config.shiro;

import cn.minalz.dao.UserRepository;
import cn.minalz.utils.Common;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author minalz
 * @Description 自定义shiro密码比较器
 * @create 2020-01-29 21:21
 */
public class MyCredentialsMatcher implements CredentialsMatcher {

    @Autowired
    private UserRepository scmciwhUserRepository;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(utoken.getPassword());
//        String username = utoken.getUsername();
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
//        SimpleAuthenticationInfo saInfo = (SimpleAuthenticationInfo)info;
        inPassword = Common.getMD5(inPassword);
//        String salt = scmciwhUserRepository.findUserByName(username).getSalt();
//        inPassword = CommunityUtil.md5(inPassword+salt);
        //进行密码的比对
        boolean flag = Objects.equals(inPassword, dbPassword);
        return flag;
    }
}
