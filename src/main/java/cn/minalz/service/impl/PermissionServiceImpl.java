package cn.minalz.service.impl;

import cn.minalz.dao.PermissionRepository;
import cn.minalz.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限表实现类
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionRepository permissionRepository;


}
