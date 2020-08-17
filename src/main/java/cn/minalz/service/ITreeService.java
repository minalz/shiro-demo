package cn.minalz.service;

import cn.minalz.dto.TreeNode;

import java.util.List;

/**
 * 查询权限树的接口
 */
public interface ITreeService {

    /**
     * 根据id获取对应的TreeNode
     * @param id
     * @return
     */
    public TreeNode getById(Long id);

    /**
     * 根据id获取对应的权限数据(单树)
     * @param id
     * @return
     */
    public TreeNode getTreeById(Long id);

    /**
     * 根据id获取对应的权限数据(多树)
     * @param id
     * @return
     */
    public List<TreeNode> getTreesById(Long id);
    /**
     * 根据父id获取对应的子node
     * @param id
     * @return
     */
    public List<TreeNode> getChildrenById(Long id);

    /**
     * 根据父id生成树结构(单树)
     * @param id
     * @return
     */
    public TreeNode generateTreeNode(Long id);

}
