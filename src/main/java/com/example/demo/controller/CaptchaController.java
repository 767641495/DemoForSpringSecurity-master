package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.utils.CaptchaUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 验证码
 * @author: Riter
 * @create: 2021-09-02 16:50
 **/

@Controller
public class CaptchaController {

    @ApiOperation("验证码生成")
    @GetMapping(value = "/captchaImage")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = CaptchaUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        // session.removeAttribute("captcha");
        session.setAttribute("captcha", verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        CaptchaUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }
}
