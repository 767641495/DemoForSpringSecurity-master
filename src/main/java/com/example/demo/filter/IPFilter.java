package com.example.demo.filter;

import com.example.demo.pojo.AjaxResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: DemoForSpringSecurity-master
 * @description: ip黑名单
 * @author: Riter
 * @create: 2021-11-04 21:29
 **/

@Component
public class IPFilter extends OncePerRequestFilter {

    private final List<String> forbiddenIpList = new ArrayList<>();

    public IPFilter() {
        // 这是本地的ip
        // this.forbiddenIpList.add("0:0:0:0:0:0:0:1");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        if (this.forbiddenIpList.contains(ip)) {
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