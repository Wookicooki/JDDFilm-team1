package com.example.jangdocdaefilm.common;

import com.example.jangdocdaefilm.dto.NowFileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class NowFileUtils {

  public List<NowFileDto> parseNowFileInfo(int idx, String id, MultipartHttpServletRequest uploadFiles) throws Exception {
    if (ObjectUtils.isEmpty(uploadFiles)) {
      return null;
    }

    List<NowFileDto> fileList = new ArrayList<>();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String current_date = simpleDateFormat.format(new Date());

    String path = "/home/ec2-user/JangDocDae/upload/now/" + current_date;

    File file = new File(path);

    if (!file.exists()) {
      file.mkdirs();
    }

    System.out.println("폴더 존재?");
    Iterator<String> iterator = uploadFiles.getFileNames();

    String newFileName;
    String originalFileExtension;
    String contentType;

    while (iterator.hasNext()) {
      List<MultipartFile> fileLists = uploadFiles.getFiles(iterator.next());
      for (MultipartFile multipartFile : fileLists) {
        if (!multipartFile.isEmpty()) {
          contentType = multipartFile.getContentType();
          if (ObjectUtils.isEmpty(contentType)) {
            break;
          } else {
            if (contentType.contains("image/jpeg")) {
              originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
              originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
              originalFileExtension = ".gif";
            } else {
              break;
            }
          }

          newFileName = System.nanoTime() + originalFileExtension;
          NowFileDto nowFile = new NowFileDto();
          nowFile.setNowIdx(idx);
          nowFile.setFileSize(multipartFile.getSize());
          nowFile.setOriginalFileName(multipartFile.getOriginalFilename());
          nowFile.setCreateId(id);
          nowFile.setStoredFileName(current_date + "/" + newFileName);

          fileList.add(nowFile);
          file = new File(path + "/" + newFileName);
          multipartFile.transferTo(file);
        }
      }
    }
    return fileList;
  }

}
