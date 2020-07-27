package cn.minalz;

import cn.minalz.dao.ScmciwhUserRepository;
import cn.minalz.model.ScmciwhUserModel;
import cn.minalz.model.ScmciwhUserRoleModel;
import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroDemoApplicationTests {
    @Autowired
    private ScmciwhUserRepository scmciwhUserRepository;

    @Test
    public void contextLoads() {
        System.out.println("ceshi");
    }

    @Test
    public void test1(){
        List<ScmciwhUserModel> all = scmciwhUserRepository.findAll();
        Object o = JSON.toJSON(all);
        System.out.println(o);
    }

}
