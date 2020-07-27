package cn.minalz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
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


}
