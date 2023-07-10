package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.common.DisFileUtils;
import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.DisFileDto;
import com.example.jangdocdaefilm.mapper.DisMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
public class DisServiceImpl implements DisService {

  @Autowired
  private DisMapper disMapper;
  // common패키지의 DisFileUtils 내려받기
  @Autowired
  private DisFileUtils disFileUtils;

  @Override
  public Page<DisDto> selectDisListNewest(int pageNum) throws Exception {
    PageHelper.startPage(pageNum, 10);
    return disMapper.selectDisListNewest();
  }
  @Override
  public Page<DisDto> selectDisListViewed(int pageNum) throws Exception {
    PageHelper.startPage(pageNum, 10);
    return disMapper.selectDisListViewed();
  }

  @Override
  public DisDto selectDisDetail(int idx) throws Exception {
    disMapper.updateDisHitCount(idx);
    return disMapper.selectDisDetail(idx);
  }

  @Override
  public void writeDis(DisDto dis, MultipartHttpServletRequest uploadFiles) throws Exception {
    disMapper.writeDis(dis);
    // 파일 업로드 구현
    List<DisFileDto> fileList = disFileUtils.parseDisFileInfo(dis.getIdx(), dis.getId(), uploadFiles);
    if (!CollectionUtils.isEmpty(fileList)) {
      disMapper.insertDisFileList(fileList);
    }
  }

  // 파일 출력
  @Override
  public List<DisFileDto> selectDisFile(int idx) throws Exception {
    return disMapper.selectDisFile(idx);
  }

  @Override
  public DisDto updateDisView(int idx) throws Exception {
    return disMapper.updateDisView(idx);
  }

  @Override
  public void updateDis(DisDto dis, MultipartHttpServletRequest updateFiles) throws Exception {
    disMapper.updateDis(dis);

    List<DisFileDto> fileList = disFileUtils.parseDisFileInfo(dis.getIdx(), dis.getId(), updateFiles);
    int disIdx = dis.getIdx();
    if (!CollectionUtils.isEmpty(fileList)) {
      disMapper.deleteDisFileList(disIdx);
      disMapper.insertDisFileList(fileList);
    }
  }

  @Override
  public void deleteDis(int idx) throws Exception {
    disMapper.deleteDis(idx);
  }

  @Override
  public void disMultiDelete(Integer[] idx) throws Exception {
    for (int i=0; i < idx.length; i++) {
      disMapper.deleteDis(idx[i]);
    }
  }

  @Override
  public void updateDisHtiCount(int idx) throws Exception {

  }
}
