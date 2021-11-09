package com.example.demo.controller;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.utils.UserAgentUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 获取客户端信息
 * @author: Riter
 * @create: 2021-11-06 21:15
 **/

@RestController
public class InfoController {

    @ApiOperation("获取信息")
    @GetMapping("/printInfo")
    public AjaxResult getInfo(HttpServletRequest request) {
        UserAgentUtils.printAll(request);
        return AjaxResult.success();
    }
}
