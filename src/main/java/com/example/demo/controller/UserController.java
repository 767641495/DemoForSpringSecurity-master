package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.example.demo.pojo.LoginBody;
import com.example.demo.pojo.LoginUser;
import com.example.demo.service.ISysUserService;
import com.example.demo.service.SysLoginService;
import com.example.demo.service.TokenService;
import com.example.demo.utils.RedisCache;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 用户CRUD
 * @author: Riter
 * @create: 2021-11-06 21:23
 **/

@RestController
@Slf4j
public class UserController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysLoginService loginService;

    @ApiOperation("用户登陆")
    @PostMapping("/toLogin")
    public AjaxResult toLogin(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        ajax.put(Constants.TOKEN, token);
        main();
        return ajax;
    }

    public String main() {
        return "main";
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public AjaxResult toRegister(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("phone") String phone, @RequestParam("inputCode") String inputCode, @RequestParam("uuid") String uuid) {
        SysUser sysUser = new SysUser(username, password, phone);
        if (!checkCode(inputCode, uuid)) {
            AjaxResult.error(sysUser.getUserName() + "验证码错误");
        }
        if (sysUserService.insertUser(sysUser)) {
            return AjaxResult.success(sysUser.getUserName() + "注册成功！");
        }
        return AjaxResult.error(sysUser.getUserName() + "注册失败！");
    }

    @ApiOperation("校验手机验证码")
    public Boolean checkCode(@RequestParam("inputCode") String inputCode, @RequestParam("uuid") String uuid) {
        //设置redis的key，这里设置为项目名:使用的字段:用户Id
        String redisKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String realCode = redisCache.getCacheObject(redisKey);
        if (realCode != null && realCode.equals(inputCode)) {
            log.info("验证码校验成功");
            return true;
        } else {
            log.info("验证码校验失败");
            return false;
        }
    }

    @ApiOperation("注销当前用户")
    @PostMapping("/delete")
    public AjaxResult toDelete(HttpServletRequest request) {
        final LoginUser loginUser = tokenService.getLoginUser(request);
        Long userId = loginUser.getUser().getUserId();
        if (sysUserService.deleteUserByUserId(userId)) {
            return AjaxResult.success("删除成功！");
        }
        return AjaxResult.error("删除失败！");
    }
}
