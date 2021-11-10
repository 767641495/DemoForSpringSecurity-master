package com.example.demo.service;

import com.example.demo.entity.SysUser;

import java.util.Date;

public interface ISysUserService {
    SysUser selectUserByUserName(String userName);

    int selectCountByUserName(String userName);

    int selectCountByPhone(String phone);

    boolean insertUser(SysUser user);

    boolean deleteUserByUserName(String userName);

    boolean deleteUserByUserId(Long userId);

    boolean updatePasswordByUserName(String userName, String password);

    /**
     * 修改用户基本信息
     *
     * @return 结果
     */
    boolean updateUserProfile(Long userId, String loginIp, Date loginDate);
}
