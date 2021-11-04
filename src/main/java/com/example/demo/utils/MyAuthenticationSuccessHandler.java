package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 登陆成功处理类
 * @author: Riter
 * @create: 2021-11-02 21:45
 **/

@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
        super.onAuthenticationSuccess(request, response, authentication);
    }
    /*

    RequestCache requestCache = new HttpSessionRequestCache();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = null;
        try {

            user = userService.getUserByMail(userDetails.getUsername());
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("userId",user.getId());
            logService.addLog("myUserDetailsService.loadUserByUsername","认证模块","低",
                    "登录","成功","邮箱为" + user.getMail() + "的用户登录成功，登录IP为" + request.getRemoteAddr(),user.getId());
        }catch (Exception e){
            logService.addLog("MyAuthenticationSuccessHandler.onAuthenticationSuccess","认证模块","高","登录","失败","保存session失败,mail为" + user.getMail(),user.getId());
        }
        String url = null;
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            url = savedRequest.getRedirectUrl();
        }
        if(url == null){
            getRedirectStrategy().sendRedirect(request,response,"/admin/adminIndex.htm");
        }

     */
}
