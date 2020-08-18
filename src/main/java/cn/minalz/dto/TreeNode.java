package cn.minalz.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树Node对象
 */
@Data
public class TreeNode {

    private Long id; // id
    private String name; // node名称
    private Long pId; // 父id
    private Boolean checked; // 是否选中
    private Integer sort; // 排序

    private List<TreeNode> children = new ArrayList<>(); // 子节点集合
}
