package com.example.demo.utils;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RiskControl {

    final int MAX_VISIT_COUNT = 100;

    @Autowired
    private RedisCache redisCache;

    private Integer num;
    private Set<String> blackSet;

    // 判断ip是否在永久黑名单或者临时黑名单里
    public void judgeIP(String ip, HttpServletResponse response) throws IOException {
        String temporary_key = Constants.TEMPORARY_ZSET + ip;
        String black_key = Constants.BLACK_SET + ip;
        String visit_key = Constants.VISIT_PREFIX + ip;

        num = redisCache.getCacheObject(visit_key);
        this.blackSet = redisCache.getCacheSet(black_key);
        Set<String> temporarySet = redisCache.getCacheZSet(temporary_key, 0, System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY * 7);
        if (blackSet != null && blackSet.contains(ip) || temporarySet != null && temporarySet.contains(ip)) {
            returnError(response, "已被加入永久黑名单");
        } else if (num != null && num > MAX_VISIT_COUNT) {
            returnError(response, "访问频繁,一分钟只能访问" + MAX_VISIT_COUNT + "次");
        }

    }

    public void updateAndJudgeIp(String ip, HttpServletResponse response) throws IOException {
        judgeIP(ip, response);

        String visit_key = Constants.VISIT_PREFIX + ip;
        if (num == null) {
            redisCache.setCacheObject(visit_key, 1, 60, TimeUnit.SECONDS);
            num = 1;
        } else if (num >= MAX_VISIT_COUNT) {
            updateInfraction(ip, response);
            returnError(response, "访问频繁,一分钟只能访问" + MAX_VISIT_COUNT + "次");
        } else {
            redisCache.increment(visit_key, 1);
        }
        log.info(ip + "第" + num + "次访问");
    }

    public void updatePhoneToIP(String ip, String phone, HttpServletResponse response) throws IOException {
        judgeIP(ip, response);
        String ipPhoneKey = Constants.IP_PHONE_PREFIX + ip;
        Long cnt = redisCache.addCacheSet(ipPhoneKey, phone);
        // 一个ip下绑定超过6台手机，增加一次违法次数
        if (cnt > 6) {
            updateInfraction(ip, response);
        }
    }

    public void updateInfraction(String ip, HttpServletResponse response) throws IOException {
        //1分钟内,连续访问MAX_VISIT_COUNT次的时候,就不让访问
        String infraction_key = Constants.INFRACTION_PREFIX + ip;
        if (redisCache.getCacheObject(infraction_key) == null) {
            redisCache.setCacheObject(infraction_key, 1, 60, TimeUnit.SECONDS);
        }
        // 违法次数
        Long infraction = redisCache.increment(infraction_key, 1);
        if (infraction >= 5 && infraction < 7) {
            redisCache.addCacheZSet(Constants.TEMPORARY_ZSET, ip, System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY * 7);
            returnError(response, ip + "已被封禁7天");
        } else if (infraction >= 7) {
            redisCache.addCacheSet(Constants.BLACK_SET, ip);
            returnError(response, ip + "已被永久封禁");
        }
    }

    public void returnError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(AjaxResult.error(400, "禁止访问的ip" + message)));
        out.flush();
    }
}

