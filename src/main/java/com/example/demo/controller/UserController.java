package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.example.demo.pojo.LoginBody;
import com.example.demo.service.ISysUserService;
import com.example.demo.service.SysLoginService;
import com.example.demo.utils.PhoneFormatCheckUtils;
import com.example.demo.utils.RedisCache;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        return ajax;
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public AjaxResult toRegister(HttpServletRequest request, SysUser sysUser, @RequestParam("inputCode") String inputCode) {

        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(sysUser.getPhone())) {
            AjaxResult.error(sysUser.getPhone() + "不是中国大陆的手机号");
        }
        if (!checkCode(request, inputCode)) {
            AjaxResult.error(sysUser.getUserName() + "验证码错误");
        }
        if (sysUserService.insertUser(sysUser)) {
            return AjaxResult.success(sysUser.getUserName() + "注册成功！");
        }
        return AjaxResult.error(sysUser.getUserName() + "注册失败！");
    }

    @ApiOperation("校验手机验证码")
    public Boolean checkCode(HttpServletRequest request, @RequestParam("inputCode") String inputCode) {
        HttpSession session = request.getSession(true);
        String uuid = session.getAttribute("uuid").toString();
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
        Long userId = Long.parseLong(request.getSession().getAttribute("userid").toString());
        if (sysUserService.deleteUserByUserId(userId)) {
            return AjaxResult.success("删除成功！");
        }
        return AjaxResult.error("删除失败！");
    }
}
