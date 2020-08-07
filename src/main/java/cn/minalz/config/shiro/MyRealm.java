package cn.minalz.config.shiro;

import cn.minalz.dao.ScmciwhUserRepository;
import cn.minalz.model.ScmciwhPermissionModel;
import cn.minalz.model.ScmciwhRoleModel;
import cn.minalz.model.ScmciwhUserModel;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 自定义的Shiro Realm 用于认证
 * @author: minalz
 * @date: 2020-07-25 00:09
 **/
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ScmciwhUserRepository scmciwhUserRepository;

    //获取权限信息的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("走了权限查询方法");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户名
        //此Principal就是彼Principal（认证时构造的）
        ScmciwhUserModel user = (ScmciwhUserModel)principals.getPrimaryPrincipal();
//        String username = user.getUsername();
        //从数据库查询用户的权限和角色
//        List<ScmciwhRoleModel> roles = scmciwhUserRepository.findRolesByUsername(username);
        Set<ScmciwhRoleModel> roles = user.getRoles();
        Set<String> rolesSet = roles.stream().map(ScmciwhRoleModel::getRoleid).collect(Collectors.toSet());
        if(rolesSet != null && rolesSet.size() > 0){
            simpleAuthorizationInfo.addRoles(rolesSet);
        }
        Set<String> permissionsSet = new HashSet<>();
        roles.forEach(x -> {
            Set<ScmciwhPermissionModel> permissions = x.getPermissions();
            Set<String> perms = permissions.stream().map(ScmciwhPermissionModel::getCode).collect(Collectors.toSet());
            permissionsSet.addAll(perms);
        });
        if(permissionsSet != null && permissionsSet.size() > 0){
            simpleAuthorizationInfo.addStringPermissions(permissionsSet);
        }
        return simpleAuthorizationInfo;
    }

    //获取认证信息的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token是封装好的用户提交的用户名密码
        String username = ((UsernamePasswordToken) token).getUsername();
//        char[] password = ((UsernamePasswordToken) token).getPassword();
//        System.out.println(password + "---");
//        System.out.println(String.valueOf(password));
        //获取用户
        ScmciwhUserModel user = scmciwhUserRepository.findByUsername(username);
        if(user == null){
            return null;
        }else{
            //封装AuthenticationInfo
//            ByteSource bsSalt = new SimpleByteSource(user.getPrivateSalt());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),null,getName());
            return authenticationInfo;
        }
    }

    /**
     * 建议重写此方法，提供唯一的缓存Key
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        ScmciwhUserModel user = (ScmciwhUserModel) principals.getPrimaryPrincipal();
        return user.getUsername();
//        return super.getAuthorizationCacheKey(principals);
    }

    /**
     * 建议重写此方法，提供唯一的缓存Key
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        ScmciwhUserModel user = (ScmciwhUserModel) principals.getPrimaryPrincipal();
        return user.getUsername();
//        return super.getAuthenticationCacheKey(principals);
    }
}
