package com.ssm.service.Impl;

import com.ssm.dao.RoleDao;
import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RoleServiceImpl implements com.ssm.service.RoleService {


    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() throws Exception {
        List<Role> roles=roleDao.findAll();
        return roles;
    }

    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }

    @Override
    public Role findById(String roleId) {
        return roleDao.findById(roleId);
    }

    @Override
    public List<Permission> findOtherPermission(String roleId) {
        return roleDao.findOtherPermission(roleId);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) {
        for (String permission:permissionIds){
            roleDao.addPermissionToRole(roleId,permission);
        }
    }


}
