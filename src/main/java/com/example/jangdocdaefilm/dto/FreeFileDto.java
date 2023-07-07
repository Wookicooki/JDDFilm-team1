package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class FreeFileDto {
    private int idx;
    private int freeIdx;
    private String originalFileName;
    private String storedFileName;
    private String createId;
    private long fileSize;
}
