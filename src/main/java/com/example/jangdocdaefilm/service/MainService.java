package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.dto.RecomDto;

import java.util.List;

public interface MainService {
    List<FreeDto> mainFree() throws Exception;

    List<NowDto> mainNow() throws Exception;

    List<DisDto> mainDis() throws Exception;
}
