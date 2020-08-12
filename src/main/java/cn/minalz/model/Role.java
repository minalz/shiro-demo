package cn.minalz.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 角色表
 * @author: minalz
 * @date: 2020-08-12 23:16
 **/
@Data
@Entity
@Table(name = "scmciwh_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -4129171581027714951L;
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 角色唯一识别码
    @Column(name = "role_code")
    private String roleCode;
    // 角色名称
    @Column(name = "role_name")
    private String roleName;
    // 备注
    private String remark;
    // 终端标识：0：PC，1：PDA
    private Integer terminate = 0;
    // 状态：0：不启用，1：启用，2：删除
    private Integer status = 0;
    // 更新用户
    @Column(name = "update_user")
    private String updateUser;
    // 更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;
    // 创建用户
    @Column(name = "create_user")
    private String createUser;
    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    // 权限集合
    @OneToMany(targetEntity = Permission.class,cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "scmciwh_role_permission",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "id")}
    )
    private Set<Permission> permissions = new HashSet<>();
}
