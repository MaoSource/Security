package com.source.security;

import com.source.bean.Permission;
import com.source.bean.User;
import com.source.mapper.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/19/10:33
 * @Description:
 */
public class MyUserDetailService implements UserDetailsService {


    private Logger logger = Logger.getLogger(MyUserDetailService.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        User user = userMapper.findByUsername(username);

        if(user!=null) {
            //根据用户名查询当前用户所有权限
            List<Permission> permList = userMapper.findPermissionByUsername(username);
            //authorities：存放所有用户权限
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            for (Permission perm : permList) {
                GrantedAuthority authority = new SimpleGrantedAuthority(perm.getPermTag());
                authorities.add(authority);
            }
            //把所有权限赋值给user
            user.setAuthorities(authorities);

            logger.info("当前用户：" + user);
        }
        return user;
    }
}
