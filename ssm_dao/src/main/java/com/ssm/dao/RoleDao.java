package com.ssm.dao;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    /**
     * 根据用户id查询出所有对应的角色.
     * @param userId
     * @return
     */
    @Select("select * from role where id in(select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions" ,column = "id",javaType = List.class,many = @Many(select = "com.ssm.dao.PermissionDao.findAllByRoleId"))
    })
     List<Role> findRoleByUserId(String userId);

    @Select("select * from role")
    List<Role> findAll();

    /**
     * 添加角色
     * @param role
     */
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role);

    @Select("select * from role where id=#{roleId}")
    Role findById(String roleId);

    @Select("select * from permission where id  not in(select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherPermission(String roleId);

    @Insert("insert into role_permission (permissionId,roleId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId,@Param("permissionId") String permissionId);
}
