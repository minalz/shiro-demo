package cn.minalz.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

}
