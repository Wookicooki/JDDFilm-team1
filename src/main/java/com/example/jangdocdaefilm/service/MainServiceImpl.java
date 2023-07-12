package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.*;
import com.example.jangdocdaefilm.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mainMapper;

    @Override
    public List<FreeDto> mainFree() throws Exception {
        return mainMapper.mainFree();
    }

    @Override
    public List<NowDto> mainNow() throws Exception {
        return mainMapper.mainNow();
    }

    @Override
    public List<DisDto> mainDis() throws Exception {
        return mainMapper.mainDis();
    }
}
