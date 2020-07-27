package cn.minalz.dao;

import cn.minalz.model.ScmciwhUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @description:
 * @author: minalz
 * @date: 2020-07-24 23:39
 **/
public interface ScmciwhUserRepository extends JpaRepository<ScmciwhUserModel,Integer>, JpaSpecificationExecutor<ScmciwhUserModel> {
}
