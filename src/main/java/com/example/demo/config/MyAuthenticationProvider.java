package com.example.demo.config;

import com.example.demo.entity.SysUser;
import com.example.demo.pojo.LoginUser;
import com.example.demo.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 自定义账号密码校验类
 * @author: Riter
 * @create: 2021-11-06 23:14
 **/

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails;
        // 根据用户名获取用户信息
        SysUser sysUser = iSysUserService.selectUserByUserName(username);
        if (sysUser == null) {
            throw new BadCredentialsException("用户名不存在");
        } else {
            Set<String> permissions = new HashSet<>();
            permissions.add("USER");
            userDetails = new LoginUser(sysUser, permissions);
            // 自定义的加密规则，用户名、输的密码和数据库保存的盐值进行加密
            if (authentication.getCredentials() == null) {
                throw new BadCredentialsException("登录名或密码错误");
            } else if (!StringUtils.equals(sysUser.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("登录名或密码错误");
            } else {
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
                result.setDetails(authentication.getDetails());
                return result;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
