package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.entity.SysUser;
import com.example.demo.service.ISysUserService;
import com.example.demo.utils.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public AjaxResult toRegister(SysUser sysUser) {
        if(sysUserService.insertUser(sysUser)) {
            return AjaxResult.success(sysUser.getUserName() + "注册成功！");
        }
        return AjaxResult.error(sysUser.getUserName() + "注册失败！");
    }

    @ApiOperation("访问主页")
    @PostMapping("/toMain")
    public String toMain() {
        return "main";
    }

    @ApiOperation("验证码生成")
    @GetMapping(value = "/code")
    public void genarateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }
}
