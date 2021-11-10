package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.example.demo.pojo.HttpStatus;
import com.example.demo.utils.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Resource
    private RedisCache redisCache;

    @Resource
    private RiskControl riskControl;

    @ApiOperation("图片验证码生成")
    @GetMapping(value = "/captchaImage")
    public AjaxResult getPhotoCaptcha(HttpServletRequest request) throws IOException {
        AjaxResult ajax = riskControl.judgeAndUpdateIp(IpUtils.getIpAddr(request));
        if (StringUtils.equals(ajax.get("code").toString(), String.valueOf(HttpStatus.ERROR))) {
            return ajax;
        }

        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String verifyCode = CaptchaUtils.generateVerifyCode(4);
        log.info("图形验证码" + verifyCode);
        ajax.put("uuid", uuid);

        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        //生成图片
        int w = 100, h = 30;
        ajax.put("img", Base64.encode(CaptchaUtils.outputImage(w, h, verifyCode)));
        return ajax;
    }

    @ApiOperation("手机验证码生成")
    @GetMapping(value = "/captchaPhone")
    public AjaxResult getPhoneCaptcha(HttpServletRequest request, @RequestParam("phone") String phone) {
        AjaxResult ajax = riskControl.judgeAndUpdateIp(IpUtils.getIpAddr(request));
        if (StringUtils.equals(ajax.get("code").toString(), String.valueOf(HttpStatus.ERROR))) {
            return ajax;
        }
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
            return AjaxResult.error(phone + "不是中国大陆的手机号");
        }
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_PHONE_KEY + phone + uuid;
        String verifyCode = CaptchaUtils.generateMathCode(8);
        log.info("手机验证码：" + verifyCode);
        ajax.put("uuid", uuid);
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        return ajax;
    }
}
