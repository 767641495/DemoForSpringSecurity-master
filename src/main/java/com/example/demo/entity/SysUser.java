package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SysUser implements Serializable{

    /** 用户ID */
    private Long userId;

    /** 用户账号 */
    private String userName;

    /** 密码 */
    private String password;

    /** 手机号*/
    private String phone;

    /** 状态*/
    private int status;

    public SysUser(String userName, String password, String phone, int status) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }
}
