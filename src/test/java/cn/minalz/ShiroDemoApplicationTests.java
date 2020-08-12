package cn.minalz;

import cn.minalz.dao.RoleRepository;
import cn.minalz.dao.UserRepository;
import cn.minalz.model.User;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroDemoApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

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

}
