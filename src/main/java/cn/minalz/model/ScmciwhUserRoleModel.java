package cn.minalz.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author niuyalong
 * @date 2019/6/30
 * @copyright 2019 Bizfocus All Rights Reserved
 **/
@Data
@Entity
@Table(name = "scmciwh_user_role", indexes = {
    @Index(name="userid", columnList="userid"),
    @Index(name="roleid", columnList="roleid")
})
public class ScmciwhUserRoleModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id ;
    /**
     * 用户id
     */
    Integer userid;

    /**
     * 岗位id
     */
    Integer roleid;


}
