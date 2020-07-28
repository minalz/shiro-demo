package cn.minalz.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "scmciwh_user", indexes = {
    @Index(name="username", columnList="username")
})
public class ScmciwhUserModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String username;//用户名
    String password;//密码
    String name;//真实名
    String phone;//手机号
    String active;//是否有效
    String werks;//工厂
    String initial;//初始密码

    @ManyToMany(targetEntity = ScmciwhRoleModel.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "scmciwh_user_role",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "userid",referencedColumnName = "id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "roleid",referencedColumnName = "id")}
    )
    private Set<ScmciwhRoleModel> roles = new HashSet<>();

}
