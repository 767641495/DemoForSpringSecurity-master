package com.example.demo.filter;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.utils.RiskControl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: DemoForSpringSecurity-master
 * @description: ip黑名单
 * @author: Riter
 * @create: 2021-11-04 21:29
 **/

@Component
public class IPFilter extends OncePerRequestFilter {

    @Autowired
    private RiskControl riskControl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        if (!riskControl.judgeIP(ip)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            out.write(objectMapper.writeValueAsString(AjaxResult.error(400, "禁止访问的ip")));
            out.flush();
        } else {
            filterChain.doFilter(request, response);
        }
    }
}