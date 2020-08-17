package cn.minalz.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 权限表
 * @author: minalz
 * @date: 2020-08-12 23:20
 **/
@Data
@Entity
@Table(name = "scmciwh_permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 6010454579839336209L;
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 菜单唯一识别码
    @Column(name = "permission_code")
    private String permissionCode;
    // 菜单描述信息
    @Column(name = "permission_name")
    private String permissionName;
    // 访问路径
    private String url;
    // 父ID
    @Column(name = "parent_id")
    private Long parentId;
    // 层级路径
    private String path;
    // 菜单排序
    private Integer sort = 0;
    // 菜单描述：0：页面，1：按钮
    private Integer type = 0;
    // 终端描述：0：PC，1：PDA
    private Integer terminate = 0;
    // 备注
    private String remark;
    // 状态：0：不启用，1：启用，2：删除
    private Integer status = 1;
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
}
