package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.mapper.FreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeServiceImpl implements FreeService {

  @Autowired
  private FreeMapper freeMapper;

  @Override
  public List<FreeDto> selectFreeList() throws Exception {
    return freeMapper.selectFreeList();
  }

  @Override
  public FreeDto selectFreeDetail(int idx) throws Exception {
    freeMapper.updateFreeHitCount(idx);
    return freeMapper.selectFreeDetail(idx);
  }

  @Override
  public void writeFree(FreeDto free) throws Exception {
    freeMapper.writeFree(free);
  }

  @Override
  public void updateFree(FreeDto free) throws Exception {
    freeMapper.updateFree(free);
  }

  @Override
  public void deleteFree(int idx) throws Exception {
    freeMapper.deleteFree(idx);
  }

  @Override
  public void freeMultiDelete(Integer[] idx) throws Exception {
    for (int i=0; i < idx.length; i++) {
      freeMapper.deleteFree(idx[i]);
    }
  }

  @Override
  public void updateFreeHtiCount(int idx) throws Exception {

  }
}
