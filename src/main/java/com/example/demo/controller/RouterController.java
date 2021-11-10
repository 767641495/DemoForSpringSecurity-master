package com.example.demo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RouterController {

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

    @ApiOperation("访问主页")
    @GetMapping("/toMain")
    public String main() {
        return "main";
    }

    @ApiOperation("访问主页")
    @PostMapping("/toMain")
    @ResponseBody
    public String toMain() {
        return "main";
    }
}
