package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCode {
    //验证码
    private String code;
    //过期时间
    private LocalDateTime expireTime;
    //图片
    private BufferedImage image;

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}