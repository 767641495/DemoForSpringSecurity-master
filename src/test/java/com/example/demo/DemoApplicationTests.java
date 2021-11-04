package com.example.demo;

import com.example.demo.entity.SysUser;
import com.example.demo.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
