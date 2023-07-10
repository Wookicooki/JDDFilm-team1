package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class QnaFileDto {
    private int idx;
    private int qnaIdx;
    private String originalFileName;
    private String storedFileName;
    private String createId;
    private long fileSize;
}
