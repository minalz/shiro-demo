package cn.minalz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@Table(name = "scmciwh_role", indexes = {
    @Index(name="werks", columnList="werks"),
    @Index(name="rolename_werks", columnList="rolename,werks"),
    @Index(name="werks_roleid", columnList="werks,roleid"),
    @Index(name="createuser_werks", columnList="createuser,werks")
})
//@IdClass(ScmciwhRoleKey.class)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ScmciwhRoleModel implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String werks;
    String rolename;
    @JsonIgnore
    Integer createuser;
    LocalDateTime createdate;
    String roleid;
    String memo;
    Integer isbacked;// 0: PC 1:PDA

    //配置多对多 单边关系维护 ScmciwhUserModel进行维护
//    @ManyToMany(mappedBy = "roles")
//    @Transient
//    private Set<ScmciwhUserModel> users = new HashSet<>();

    @OneToMany(targetEntity = ScmciwhPermissionModel.class,cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_role_perm",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "perm_id",referencedColumnName = "id")}
    )
    private Set<ScmciwhPermissionModel> permissions = new HashSet<>();


}
