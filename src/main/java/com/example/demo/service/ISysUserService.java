package com.example.demo.service;

import com.example.demo.entity.SysUser;

public interface ISysUserService {
    SysUser selectUserByUserName(String userName);

    boolean insertUser(SysUser user);

    boolean deleteUserByUserName(String userName);

    boolean deleteUserByUserId(Long userId);

    boolean updatePasswordByUserName(String userName, String password);
}
