package cn.minalz.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_permission")
public class ScmciwhPermissionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    private String url;
//    private String permType;//0:资源菜单 1资源下的权限
}
