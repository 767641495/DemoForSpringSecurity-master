package com.example.demo.pojo;

import lombok.Data;

@Data
public class ImageResult {
    private int xpos;
    private int ypos;
    private int cutImageWidth;
    private int cutImageHeight;
    private String cutImage;
    private String oriImage;
}
