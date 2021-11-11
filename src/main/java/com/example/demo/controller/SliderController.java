package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.example.demo.pojo.ImageResult;
import com.example.demo.utils.ImgUtil;
import com.example.demo.utils.IpUtils;
import com.example.demo.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * @author Riter
 * @description 滑块控制
 * @date 2021/11/10 11:16 下午
 */
@Slf4j
@RestController
@RequestMapping("/slider")
public class SliderController {

    @Resource
    private RedisCache redisCache;

    private int xPosCache = 0;

    @RequestMapping("/judge")
    public AjaxResult judge(HttpServletRequest request) {
        AjaxResult ajax = AjaxResult.success();
        String infraction_key = Constants.INFRACTION_PREFIX + IpUtils.getIpAddr(request);
        Integer infraction = redisCache.getCacheObject(infraction_key);
        if (infraction != null && infraction > 0) {
            ajax.put(Constants.CAPTCHA_FLAG, true);
        } else {
            ajax.put(Constants.CAPTCHA_FLAG, false);
        }
        return ajax;
    }

    @RequestMapping("/image")
    public AjaxResult image() {
        ImageResult imageResult;
        try {
            imageResult = new ImgUtil().imageResult();
            xPosCache = imageResult.getXpos();
            return AjaxResult.success(imageResult);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return AjaxResult.error();
        }
    }

    @RequestMapping("/verification")
    public AjaxResult verification(@RequestParam("moveX") int moveX) {
        int MOVE_CHECK_ERROR = 2;
        if ((moveX < (xPosCache + MOVE_CHECK_ERROR))
                && (moveX > (xPosCache - MOVE_CHECK_ERROR))) {
            log.info("验证正确");
            return AjaxResult.success();
        }
        return AjaxResult.error("验证错误");
    }
}
