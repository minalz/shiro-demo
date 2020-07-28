package cn.minalz.dao;

import cn.minalz.model.ScmciwhRoleModel;
import cn.minalz.model.ScmciwhUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @description:
 * @author: minalz
 * @date: 2020-07-24 23:39
 **/
public interface ScmciwhUserRepository extends JpaRepository<ScmciwhUserModel,Integer>, JpaSpecificationExecutor<ScmciwhUserModel> {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    ScmciwhUserModel findByUsername(String username);

    /**
     * 查询所有对应的角色
     * @param username
     * @return
     */
    List<ScmciwhRoleModel> findRolesByUsername(String username);

}
