package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.mapper.NowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NowServiceImpl implements NowService {

  @Autowired
  private NowMapper nowMapper;

  @Override
  public List<NowDto> selectNowList() throws Exception {
    return nowMapper.selectNowList();
  }

  @Override
  public NowDto selectNowDetail(int idx) throws Exception {
    nowMapper.updateNowHitCount(idx);
    return nowMapper.selectNowDetail(idx);
  }

  @Override
  public void writeNow(NowDto now) throws Exception {
    nowMapper.writeNow(now);
  }

  @Override
  public void updateNow(NowDto now) throws Exception {
    nowMapper.updateNow(now);
  }

  @Override
  public void deleteNow(int idx) throws Exception {
    nowMapper.deleteNow(idx);
  }

  @Override
  public void updateNowHtiCount(int idx) throws Exception {

  }
}
