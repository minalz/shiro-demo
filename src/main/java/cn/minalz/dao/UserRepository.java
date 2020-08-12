package cn.minalz.dao;

import cn.minalz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @description:
 * @author: minalz
 * @date: 2020-07-24 23:39
 **/
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查询所有对应的角色
     * @param username
     * @return
     */
    List<User> findRolesByUsername(String username);

}
