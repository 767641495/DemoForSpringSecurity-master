package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.utils.IdUtils;
import com.google.code.kaptcha.Producer;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 验证码
 * @author: Riter
 * @create: 2021-09-02 16:50
 **/

@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();

        // 获得随机验证码
        String capStr = captchaProducer.createText();
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        ajax.put("uuid", uuid);
        // 根据验证码生成图片
        BufferedImage image = captchaProducer.createImage(capStr);

        // 转换流信息写出
        try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
            ajax.put("img", Base64.encode(os.toByteArray()));
            try {
                ImageIO.write(image, "jpg", os);
            } catch (IOException e) {
                return AjaxResult.error(e.getMessage());
            }


            // 创建字节数组用于存放图片信息
            byte[] numCodeImgByte = os.toByteArray();


            // 通过response设定响应请求类型
            // no-store用于防止重要的信息被无意的发布。在请求消息中发送将使得请求和响应消息都不使用缓存。
            response.setHeader("Cache-Control", "no-store");
            // no-cache指示请求或响应消息不能缓存
            response.setHeader("Pragma", "no-cache");
            /* expires是response的一个属性,它可以设置页面在浏览器的缓存里保存的时间 ,超过设定的时间后就过期 。过期后再次
             * 浏览该页面就需要重新请求服务器发送页面数据，如果在规定的时间内再次访问次页面 就不需从服务器传送直接从缓存中读取。
             * */
            response.setDateHeader("Expires", 0);
            // servlet接受request请求后接受图片形式的响应
            response.setContentType("image/jpg");

            //通过response获得输出流
            try (ServletOutputStream sos = response.getOutputStream()) {
                sos.write(numCodeImgByte);
            }
        }

        return ajax;
    }
}
