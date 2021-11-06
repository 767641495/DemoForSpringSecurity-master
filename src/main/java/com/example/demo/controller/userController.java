package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.pojo.AjaxResult;
import com.example.demo.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 用户CRUD
 * @author: Riter
 * @create: 2021-11-06 21:23
 **/

@RestController
public class userController {
    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public AjaxResult toRegister(SysUser sysUser) {
        if (sysUserService.insertUser(sysUser)) {
            return AjaxResult.success(sysUser.getUserName() + "注册成功！");
        }
        return AjaxResult.error(sysUser.getUserName() + "注册失败！");
    }

    @ApiOperation("根据用户id删除用户")
    @PostMapping("/delete")
    public AjaxResult deleteUserById(Long userId) {
        if (sysUserService.deleteUserByUserId(userId)) {
            return AjaxResult.success("删除成功！");
        }
        return AjaxResult.error("删除失败！");
    }
}
