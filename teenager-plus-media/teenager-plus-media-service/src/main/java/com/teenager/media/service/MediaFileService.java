package com.teenager.media.service;

import com.teenager.base.model.PageParams;
import com.teenager.base.model.PageResult;
import com.teenager.base.model.RestResponse;
import com.teenager.media.model.dto.UploadFileParamsDto;
import com.teenager.media.model.dto.UploadFileResult;
import com.teenager.media.model.po.MediaFiles;

import java.io.File;

public interface MediaFileService {

  public String upload(Long userId, UploadFileParamsDto uploadFileParamsDto,String localFilePath,String objectName);

 public MediaFiles addMediaFilesToDb(Long userId, String md5, UploadFileParamsDto uploadFileParamsDto, String bucket_video, String objectName);

 public boolean addMediaFilesToMinIO(String localFilePath,String mimeType,String bucket, String objectName);
}
