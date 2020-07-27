package cn.minalz.dao;

import cn.minalz.model.ScmciwhRoleModel;
import cn.minalz.model.ScmciwhUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @description:
 * @author: minalz
 * @date: 2020-07-24 23:39
 **/
public interface ScmciwhRoleRepository extends JpaRepository<ScmciwhRoleModel,Integer>, JpaSpecificationExecutor<ScmciwhRoleModel> {
}
