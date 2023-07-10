package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.common.QnaFileUtils;
import com.example.jangdocdaefilm.dto.QnaDto;
import com.example.jangdocdaefilm.dto.QnaFileDto;
import com.example.jangdocdaefilm.mapper.QnaMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
public class QnaServiceImpl implements QnaService {

  @Autowired
  private QnaMapper qnaMapper;
  // common패키지의 QnaFileUtils 내려받기
  @Autowired
  private QnaFileUtils qnaFileUtils;

  @Override
  public Page<QnaDto> selectQnaListNewest(int pageNum) throws Exception {
    PageHelper.startPage(pageNum, 10);
    return qnaMapper.selectQnaListNewest();
  }

  @Override
  public QnaDto selectQnaDetail(int idx) throws Exception {
    return qnaMapper.selectQnaDetail(idx);
  }
  @Override
  public void writeQna(QnaDto qna, MultipartHttpServletRequest uploadFiles) throws Exception {
    qnaMapper.writeQna(qna);
    // 파일 업로드 구현
    List<QnaFileDto> fileList = qnaFileUtils.parseQnaFileInfo(qna.getIdx(), qna.getId(), uploadFiles);
    if (!CollectionUtils.isEmpty(fileList)) {
      qnaMapper.insertQnaFileList(fileList);
    }
  }

  // 파일 출력
  @Override
  public List<QnaFileDto> selectQnaFile(int idx) throws Exception {
    return qnaMapper.selectQnaFile(idx);
  }

  @Override
  public QnaDto updateQnaView(int idx) throws Exception {
    return qnaMapper.updateQnaView(idx);
  }

  @Override
  public void updateQna(QnaDto qna, MultipartHttpServletRequest updateFiles) throws Exception {
    qnaMapper.updateQna(qna);

    List<QnaFileDto> fileList = qnaFileUtils.parseQnaFileInfo(qna.getIdx(), qna.getId(), updateFiles);
    int qnaIdx = qna.getIdx();
    if (!CollectionUtils.isEmpty(fileList)) {
      qnaMapper.deleteQnaFileList(qnaIdx);
      qnaMapper.insertQnaFileList(fileList);
    }
  }

  @Override
  public void deleteQna(int idx) throws Exception {
    qnaMapper.deleteQna(idx);
  }

  @Override
  public void qnaMultiDelete(Integer[] idx) throws Exception {
    for (int i=0; i < idx.length; i++) {
      qnaMapper.deleteQna(idx[i]);
    }
  }

}
