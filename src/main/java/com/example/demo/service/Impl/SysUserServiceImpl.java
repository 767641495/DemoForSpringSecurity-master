package com.example.demo.service.Impl;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    @Override
    public boolean insertUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("Legal");
        return sysUserMapper.insertUser(user);
    }

    @Override
    public boolean deleteUserByUserName(String userName) {
        return sysUserMapper.deleteUserByUserName(userName);
    }

    @Override
    public boolean updatePasswordByUserName(String userName, String password) {
        return sysUserMapper.updatePasswordByUserName(userName, passwordEncoder.encode(password));
    }
}
