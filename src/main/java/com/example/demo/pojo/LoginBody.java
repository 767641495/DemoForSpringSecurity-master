package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: DemoForSpringSecurity-master
 * @description: 用户登陆对象
 * @author: Riter
 * @create: 2021-11-07 08:52
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
