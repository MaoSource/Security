package com.source.mapper;

import com.source.bean.Permission;
import com.source.bean.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    User findByUsername(String Username);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * 查询当前用户拥有的权限
     */
    public List<Permission> findPermissionByUsername(String username);

    public void updatePassword(User user);
}