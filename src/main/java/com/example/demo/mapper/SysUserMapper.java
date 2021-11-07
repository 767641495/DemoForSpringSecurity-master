package com.example.demo.mapper;

import com.example.demo.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SysUserMapper {
    SysUser selectUserByUserName(String userName);

    boolean insertUser(SysUser user);

    boolean deleteUserByUserName(String userName);

    boolean deleteUserByUserId(Long userId);

    boolean updatePasswordByUserName(String userName, String password);

    boolean updateUserProfile(Long userId, String loginIp, Date loginDate);
}
