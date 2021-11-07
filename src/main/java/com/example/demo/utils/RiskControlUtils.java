package com.example.demo.utils;

import com.example.demo.pojo.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RiskControlUtils {

    @Autowired
    private RedisCache redisCache;

    public boolean judgeIP(String ip) {
        String visit_key = Constants.VISIT_PREFIX + ip;
        String message;
        String num = redisCache.getCacheObject(visit_key);
        if (num == null) {
            redisCache.setCacheObject(visit_key, "1", 60, TimeUnit.SECONDS);
            message = "第1次访问";
        } else if (StringUtils.equals(num, "10")) {
            //1分钟内,连续访问10次的时候,就不让访问
            message = "访问频繁,一分钟只能访问10次";
            String infraction_key = Constants.INFRACTION_PREFIX + ip;
            if(redisCache.getCacheObject(infraction_key) == null) {
                redisCache.setCacheObject(infraction_key, "1", 60, TimeUnit.SECONDS);
            }
            Long infraction = redisCache.increment(infraction_key, 1);
            if(infraction >= 5 && infraction <7) {
                redisCache.zInsert(Constants.FORBIDDEN_SET, ip,System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY * 7);
            } else if(infraction > 7){
                redisCache.addCacheSet(Constants.BLACK_SET, ip);
            }
            log.info(visit_key + message);
            return false;
        } else {
            redisCache.increment(visit_key, 1);
            //每访问一次进行+1
            message = "第" + num + "次访问";
        }
        log.info(visit_key + message);
        return true;
    }
}

