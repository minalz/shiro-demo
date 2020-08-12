package cn.minalz.dao;

import cn.minalz.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @description:权限持久层类
 * @author: minalz
 * @date: 2020-07-24 23:39
 **/
public interface PermissionRepository extends JpaRepository<Permission,Long>, JpaSpecificationExecutor<Permission> {
}
