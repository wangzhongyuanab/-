package com.ssm.service.Impl;

import com.ssm.dao.PermissionDao;
import com.ssm.domain.Permission;
import com.ssm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private PermissionDao permissionDao;


    @Override
    public List<Permission> findAll() {
        List<Permission> permissions=permissionDao.findAll();
        return permissions;
    }

    @Override
    public void save(Permission permission) {
        permissionDao.save(permission);
    }
}
