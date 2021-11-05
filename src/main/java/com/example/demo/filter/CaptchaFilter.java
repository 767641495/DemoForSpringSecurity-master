package com.example.demo.filter;

import com.example.demo.pojo.Constants;
import com.example.demo.utils.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // /authentication/form是认证时的请求接口，验证码校验只需要匹配这个接口即可
        if (StringUtils.equals("/authentication/form", request.getRequestURI()) &&
                StringUtils.equalsAnyIgnoreCase(request.getMethod(), "post")) {
            String result = validate(request);
            if (!StringUtils.equals(result, "success")) {
                logger.error(result);
                throw new AuthenticationException();
            }
            validate(request);
        }
        // 无异常即校验成功，放行。
        filterChain.doFilter(request, response);
    }

    private String validate(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String uuid = session.getAttribute("uuid").toString();
        //设置redis的key，这里设置为项目名:使用的字段:用户Id
        String redisKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(redisKey);

        // 从客户端接收到的验证码
        String captchaParam = request.getParameter("captcha");

        if (StringUtils.isEmpty(captchaParam)) {
            return "验证码不能为空";
        }

        if (captcha == null) {
            return "验证码不存在";
        }

        if (!StringUtils.equalsAnyIgnoreCase(captcha.toString(), captchaParam)) {
            return "验证码不匹配";
        }
        // 校验成功之后，从session中移除验证码
        session.removeAttribute("captcha");

        return "success";
    }
}
