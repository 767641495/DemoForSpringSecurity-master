package com.example.demo.mapper;

import com.example.demo.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SysUserMapper {
    SysUser selectUserByUserName(String userName);

    boolean insertUser(SysUser user);

    boolean deleteUserByUserName(String userName);

    boolean deleteUserByUserId(Long userId);

    boolean updatePasswordByUserName(@Param("userName") String userName,@Param("password") String password);

    boolean updateUserProfile(@Param("userId") Long userId, @Param("loginIp")  String loginIp, @Param("loginDate")  Date loginDate);

    int selectCountByUserName(String userName);

    int selectCountByPhone(String phone);
}
