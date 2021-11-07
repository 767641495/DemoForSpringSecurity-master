package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.example.demo.pojo.LoginBody;
import com.example.demo.service.SysLoginService;
import com.example.demo.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RouterController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation("访问登陆页面")
    @GetMapping({"/", "/toLogin"})
    public String login() {
        return "login";
    }

    @ApiOperation("Post访问登陆页面")
    @PostMapping("/toLogin")
    public AjaxResult toLogin(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
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

    @ApiOperation("访问主页")
    @PostMapping("/toMain")
    public String toMain() {
        return "main";
    }

    @ApiOperation("访问主页")
    @GetMapping("/toMain")
    public String main() {
        return "main";
    }

    @ApiOperation("登陆后才可以访问的资源")
    @GetMapping("/toHide")
    public String toHide() {
        return "hide";
    }
}
