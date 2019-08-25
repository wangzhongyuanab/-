package com.ssm.service.Impl;

import com.ssm.dao.UserDao;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密处理
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        UserInfo userInfo=userDao.findById(id);
        return userInfo;
    }

    @Override
    public List<Role> findOtherRole(String userId) {
        List<Role> roles=userDao.findOtherRole(userId);
        return roles;
    }

    @Override
    public void addRoleToUser(String userId, String[] roleIds) {
        for (String roleId:roleIds){
            userDao.addRoleToUser(userId,roleId);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Role> roles = userInfo.getRoles();
        List<SimpleGrantedAuthority> authorities = getAuthority(roles);
        //处理自己的用户对象封装成UserDetails
        User user=new User(userInfo.getUsername(),userInfo.getPassword(),
                userInfo.getStatus()==0 ? false : true,true,true,true,authorities);
        return user;
    }

    //返回一个list集合，集合中装入的是角色描述。
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }

}
