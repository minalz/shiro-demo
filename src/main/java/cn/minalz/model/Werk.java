package cn.minalz.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 工厂表
 * @author: minalz
 * @date: 2020-08-12 23:11
 **/
@Data
@Entity
@Table(name = "scmciwh_werk")
public class Werk implements Serializable {
    private static final long serialVersionUID = -8555405122218479323L;
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 工厂唯一识别码
    @Column(name = "werk_code")
    private String werkCode;
    // 工厂名称
    @Column(name = "werk_name")
    private String werkName;
    // 备注
    private String remark;
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
}

