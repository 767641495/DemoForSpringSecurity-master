package com.example.demo.service.Impl;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    @Override
    public boolean insertUser(SysUser user) {
        user.setStatus(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return sysUserMapper.insertUser(user);
    }

    @Override
    public boolean deleteUserByUserName(String userName) {
        return sysUserMapper.deleteUserByUserName(userName);
    }

    @Override
    public boolean deleteUserByUserId(Long userId) {
        return sysUserMapper.deleteUserByUserId(userId);
    }

    @Override
    public boolean updatePasswordByUserName(String userName, String password) {
        return sysUserMapper.updatePasswordByUserName(userName, passwordEncoder.encode(password));
    }

    /**
     * 修改用户基本信息
     *
     * @return 结果
     */
    @Override
    public boolean updateUserProfile(Long userId, String loginIp, Date loginDate) {
        return sysUserMapper.updateUserProfile(userId, loginIp, loginDate);
    }

    @Override
    public int selectCountByUserName(String userName) {
        return sysUserMapper.selectCountByUserName(userName);
    }

    @Override
    public int selectCountByPhone(String phone) {
        return sysUserMapper.selectCountByPhone(phone);
    }
}
