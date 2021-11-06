package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.pojo.AjaxResult;
import com.example.demo.service.ISysUserService;
import com.example.demo.utils.UserAgentUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Api("路由")
@Controller
public class RouterController {

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("访问登陆页面")
    @GetMapping({"/", "/toLogin"})
    public String login() {
        return "login";
    }

    @ApiOperation("访问注册页面")
    @GetMapping("/toRegister")
    public String register() {
        return "register";
    }

    @ApiOperation("访问失败页面")
    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    @ResponseBody
    public AjaxResult toRegister(SysUser sysUser) {
        if (sysUserService.insertUser(sysUser)) {
            return AjaxResult.success(sysUser.getUserName() + "注册成功！");
        }
        return AjaxResult.error(sysUser.getUserName() + "注册失败！");
    }

    @ApiOperation("访问主页")
    @PostMapping("/toMain")
    public String toMain() {
        return "main";
    }

    @ApiOperation("访问主页")
    @GetMapping("/toMain")
    public String getMain() {
        return "main";
    }

    @ApiOperation("登陆后才可以访问的资源")
    @GetMapping("/toHide")
    public String toHide() {
        return "hide";
    }

    @ApiOperation("获取信息")
    @GetMapping("/getInfo")
    @ResponseBody
    public AjaxResult getInfo(HttpServletRequest request) {
        AjaxResult ajax = new AjaxResult();
        UserAgentUtils.printAll(request);
        return ajax;
    }
}
