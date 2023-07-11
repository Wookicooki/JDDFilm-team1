package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.dto.RecomDto;

import java.util.List;

public interface MyService {
    List<FreeDto> myFree(String id) throws Exception;

    List<NowDto> myNow(String id) throws Exception;

    List<DisDto> myDis(String id) throws Exception;

    List<RecomDto> getRecoms(String userName) throws Exception;

    List<String> getRecomsLike(String userName) throws Exception;

    String setPosterPath(String url) throws Exception;

    List<RecomDto> getLikeRecom() throws Exception;
}
