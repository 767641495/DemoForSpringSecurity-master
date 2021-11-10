package com.example.demo.config;

import com.example.demo.service.Impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 自定义账号密码校验类
 * @author: Riter
 * @create: 2021-11-06 23:14
 **/

@Component
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        // authentication.getCredentials()是密码
        // authentication.getPrincipal()是账号

        if (!encoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            log.error("密码错误");
            throw new BadCredentialsException("登录名或密码错误");
        } else {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
