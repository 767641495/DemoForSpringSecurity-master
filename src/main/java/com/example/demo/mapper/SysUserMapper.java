package com.example.demo.mapper;

import com.example.demo.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {
    SysUser selectUserByUserName(String userName);

    boolean insertUser(SysUser user);

    boolean deleteUserByUserName(String userName);

    boolean updatePasswordByUserName(String userName, String password);
}
