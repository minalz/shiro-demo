package cn.minalz;

import cn.minalz.dao.PermissionRepository;
import cn.minalz.dao.RoleRepository;
import cn.minalz.dao.UserRepository;
import cn.minalz.dto.TreeNode;
import cn.minalz.model.Permission;
import cn.minalz.model.User;
import cn.minalz.service.ITreeService;
import cn.minalz.service.impl.PermissionServiceImpl;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroDemoApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ITreeService treeService;

    @Test
    public void contextLoads() {
        System.out.println("ceshi");
    }

    @Test
    @Transactional
    public void test1(){
//        List<ScmciwhUserModel> all = scmciwhUserRepository.findAll();
//        Object o = JSON.toJSON(all);
        User scmciwhUserModel = userRepository.findById(1l).get();
        Object o = JSON.toJSON(scmciwhUserModel);
        System.out.println(o);
        System.out.println("--------------");

    }

    @Test
    public void test2(){
        List<Permission> all = permissionRepository.findAll();
        Object o = JSON.toJSON(all);
        System.out.println(o);
    }

    @Test
    public void test3(){
        TreeNode treeById = treeService.getTreeById(1l);
        Object o = JSON.toJSON(treeById);
        System.out.println(o);
    }

}
