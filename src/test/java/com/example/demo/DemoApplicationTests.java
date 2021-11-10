package com.example.demo;

import com.example.demo.entity.SysUser;
import com.example.demo.pojo.Constants;
import com.example.demo.service.ISysUserService;
import com.example.demo.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RedisCache redisCache;
    @Test
    void contextLoads() {
//        RedisCache redisCache=new RedisCache();
//        long d1=123;
        redisCache.addCacheZSet("12311", "123", 111-604800000);
    }

    @Test
    void testInsert() {
        SysUser user = new SysUser("root3", "root", "17366636923");
        sysUserService.insertUser(user);
    }

    @Test
    void testUpdate() {
        sysUserService.updatePasswordByUserName("root", "123456");
    }

}
