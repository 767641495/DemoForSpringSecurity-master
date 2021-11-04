package com.example.demo.filter;

import com.example.demo.exception.CaptchaException;
import com.example.demo.utils.MyAuthenticationFailureHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // /authentication/form是认证时的请求接口，验证码校验只需要匹配这个接口即可
        if (StringUtils.equals("/authentication/form", request.getRequestURI()) &&
                StringUtils.equalsAnyIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(request);
            } catch (AuthenticationException e) {
                // 校验失败时，让失败的处理器去处理
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        // 无异常即校验成功，放行。
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws CaptchaException {
        HttpSession session = request.getSession(true);
        // 从session中获取验证码
        Object captcha = session.getAttribute("captcha");
        // 从客户端接收到的验证码
        String captchaParam = request.getParameter("captcha");

        if (StringUtils.isEmpty(captchaParam)) {
            throw new CaptchaException("验证码不能为空");
        }

        if (captcha == null) {
            throw new CaptchaException("验证码不存在");
        }

        if (!StringUtils.equalsAnyIgnoreCase(captcha.toString(), captchaParam)) {
            throw new CaptchaException("验证码不匹配");
        }
        // 校验成功之后，从session中移除验证码
        session.removeAttribute("captcha");
    }
}
