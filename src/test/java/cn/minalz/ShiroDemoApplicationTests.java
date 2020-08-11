package cn.minalz;

import cn.minalz.dao.ScmciwhRoleRepository;
import cn.minalz.dao.ScmciwhUserRepository;
import cn.minalz.model.ScmciwhUserModel;
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
    private ScmciwhUserRepository scmciwhUserRepository;
    @Autowired
    private ScmciwhRoleRepository scmciwhRoleRepository;

    @Test
    public void contextLoads() {
        System.out.println("ceshi");
    }

    @Test
    @Transactional
    public void test1(){
//        List<ScmciwhUserModel> all = scmciwhUserRepository.findAll();
//        Object o = JSON.toJSON(all);
        ScmciwhUserModel scmciwhUserModel = scmciwhUserRepository.findById(771).get();
        Object o = JSON.toJSON(scmciwhUserModel);
        System.out.println(o);
        System.out.println("--------------");

    }

}
