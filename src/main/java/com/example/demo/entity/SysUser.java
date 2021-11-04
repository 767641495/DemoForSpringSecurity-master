package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    /** 用户ID */
    private Long userId;

    /** 用户账号 */
    private String userName;

    /** 密码 */
    private String password;

    /** 手机号*/
    private String phone;

    /** 状态*/
    private String status;

    public SysUser(String userName, String password, String phone, String status) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }
}
