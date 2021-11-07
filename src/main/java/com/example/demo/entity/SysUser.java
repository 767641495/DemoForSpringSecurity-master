package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
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

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private Date loginDate;

    public SysUser(String userName, String password, String phone, int status) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }

    public SysUser(Long userId, String userName, String password, String phone, int status) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }
}
