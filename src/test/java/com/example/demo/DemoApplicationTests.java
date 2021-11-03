package com.example.demo;

import com.example.demo.domain.SysUser;
import com.example.demo.service.ISysUserService;
import com.example.demo.service.Impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ISysUserService sysUserService;

    @Test
    void contextLoads() {

    }

    @Test
    void testInsert() {
        SysUser user = new SysUser("root3", "root", "17366636923", "OK");
        sysUserService.insertUser(user);
    }

    @Test
    void testUpdate() {
        sysUserService.updatePasswordByUserName("root", "123456");
    }

}
