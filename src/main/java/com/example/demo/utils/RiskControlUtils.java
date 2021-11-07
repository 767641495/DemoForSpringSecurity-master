package com.example.demo.utils;

import com.example.demo.pojo.AjaxResult;
import com.example.demo.pojo.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RiskControlUtils {

    @Autowired
    private RedisCache redisCache;

    public boolean judgeIP(String ip) {
        String key = Constants.VISIT_PREFIX + ip;
        String message;
        String num = redisCache.getCacheObject(key);
        if (num == null) {
            redisCache.setCacheObject(key, "1", 60, TimeUnit.SECONDS);
            message = "第1次访问";
        } else if (StringUtils.equals(num, "10")) {
            //1分钟内,连续访问10次的时候,就不让访问
            message = "访问频繁,一分钟只能访问10次";
            Long infraction = redisCache.increment(Constants.INFRACTION_PREFIX + ip, 1);

            log.info(key + message);
            return false;
        } else {
            redisCache.increment(key, 1);
            //每访问一次进行+1
            message = "第" + num + "次访问";
        }
        log.info(key + message);
        return true;
    }
}

