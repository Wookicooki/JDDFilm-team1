package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.mapper.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyServiceImpl implements MyService {

  @Autowired
  private MyMapper myMapper;

  @Override
  public List<FreeDto> myFree(String id) throws Exception {
    return myMapper.myFree(id);
  }

  @Override
  public List<NowDto> myNow(String id) throws Exception {
    return myMapper.myNow(id);
  }

  @Override
  public List<DisDto> myDis(String id) throws Exception {
    return myMapper.myDis(id);
  }
}
