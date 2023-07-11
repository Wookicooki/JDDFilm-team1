package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class NowFileDto {
    private int idx;
    private int nowIdx;
    private String originalFileName;
    private String storedFileName;
    private String createId;
    private long fileSize;
}
