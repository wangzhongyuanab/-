package com.ssm.service;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll() throws Exception;

    void save(Role role) throws Exception;

    Role findById(String roleId);

    List<Permission> findOtherPermission(String roleId);

    void addPermissionToRole(String roleId, String[] permissions);
}
