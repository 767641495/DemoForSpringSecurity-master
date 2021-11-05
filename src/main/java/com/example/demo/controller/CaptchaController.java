package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.example.demo.utils.CaptchaUtils;
import com.example.demo.utils.RedisCache;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 验证码
 * @author: Riter
 * @create: 2021-09-02 16:50
 **/

@RestController
@Slf4j
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;

    @ApiOperation("图片验证码生成")
    @GetMapping(value = "/captchaImage")
    public void getPhotoCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // 生成随机字串
        String verifyCode = CaptchaUtils.generateVerifyCode(4);
        log.info(verifyCode);
        // 存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("uuid", uuid);
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        //生成图片
        int w = 100, h = 30;
        CaptchaUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    @ApiOperation("手机验证码生成")
    @GetMapping(value = "/captchaPhone")
    public void getPhoneCaptcha(HttpServletRequest request) {
        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // 生成随机字串
        String verifyCode = CaptchaUtils.generateMathCode(8);
        log.info(verifyCode);
        // 存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("uuid", uuid);
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @ApiOperation("校验手机验证码")
    @GetMapping(value = "/checkCode")
    public AjaxResult checkCode(HttpServletRequest request, @RequestParam("inputCode") String inputCode) {
        HttpSession session = request.getSession(true);
        String uuid = session.getAttribute("uuid").toString();
        //设置redis的key，这里设置为项目名:使用的字段:用户Id
        String redisKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String realCode = redisCache.getCacheObject(redisKey);
        if (realCode != null && realCode.equals(inputCode)) {
            log.info("验证码校验成功");
            return AjaxResult.success();
        } else {
            log.info("验证码校验失败");
            return AjaxResult.error();
        }
    }


}
