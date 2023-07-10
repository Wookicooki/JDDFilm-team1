package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class DisFileDto {
    private int idx;
    private int disIdx;
    private String originalFileName;
    private String storedFileName;
    private String createId;
    private long fileSize;
}
