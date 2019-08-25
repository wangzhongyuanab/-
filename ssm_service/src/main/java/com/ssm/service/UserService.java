package com.ssm.service;


import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserInfo> findAll() throws Exception;

    void save(UserInfo userInfo) throws  Exception;

    UserInfo findById(String id) throws  Exception;

    List<Role> findOtherRole(String userId);

    void addRoleToUser(String userId, String[] roleIds);
}
