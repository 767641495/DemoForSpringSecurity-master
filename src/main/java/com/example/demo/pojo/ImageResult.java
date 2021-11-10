package com.example.demo.pojo;

import lombok.Data;

/**
 * @author Riter
 * @description 验证码模块的pojo
 * @date 2021/11/10 11:13 下午
 */
@Data
public class ImageResult {
    private int xpos;
    private int ypos;
    private int cutImageWidth;
    private int cutImageHeight;
    private String cutImage;
    private String oriImage;
}
