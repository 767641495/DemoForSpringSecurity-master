package com.example.demo.utils;

import java.util.UUID;

/**
 * @program: DemoForSpringSecurity-master
 * @description: ID生成器
 * @author: Riter
 * @create: 2021-09-02 17:40
 **/

public class IdUtils
{
    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString();
    }
}

