package com.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.HttpStatus;
import com.example.demo.pojo.LoginUser;
import com.example.demo.service.TokenService;
import com.example.demo.utils.ServletUtils;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 自定义退出成功处理类
 * @author: Riter
 * @create: 2021-11-07 14:38
 **/

@Component
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
        if (StringUtils.isNotNull(loginUser)) {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
