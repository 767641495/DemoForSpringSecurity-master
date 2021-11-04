package com.example.demo.service.Impl;

import com.example.demo.entity.SysUser;
import com.example.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author Riter
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ISysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (sysUser == null) {
            //数据库中没有，认证失败
            throw new UsernameNotFoundException("该用户不存在");
        }
        return new User(username, sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
