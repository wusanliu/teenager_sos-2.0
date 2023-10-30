package com.teenager.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.teenager.base.model.PageParams;
import com.teenager.base.model.PageResult;
import com.teenager.base.model.RestResponse;
import com.teenager.media.mapper.MediaFilesMapper;

import com.teenager.media.model.dto.UploadFileParamsDto;
import com.teenager.media.model.dto.UploadFileResult;
import com.teenager.media.model.po.MediaFiles;

import com.teenager.media.service.MediaFileService;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Slf4j
 @Service
public class MediaFileServiceImpl implements MediaFileService {

 @Autowired
 MediaFilesMapper mediaFilesMapper;

 @Autowired
 MinioClient minioClient;

 //存储普通文件
 @Value("${minio.bucket.files}")
 private String bucket_mediafiles;

 @Override
 public String upload(Long userId, UploadFileParamsDto uploadFileParamsDto, String localFilePath, String objectName) {
//   将文件上传到minio
  String filename = uploadFileParamsDto.getFilename();
  String s = filename.substring(filename.lastIndexOf("."));
  String mimeType = getMimeType(s);
  String fileMd5 = getFileMd5(new File(localFilePath));
  if (StringUtils.isEmpty(objectName)) {
   objectName = getDefaultFolderPath() + fileMd5 + s;
  }
  boolean b = addMediaFilesToMinIO(localFilePath, mimeType, bucket_mediafiles, objectName);
  if (!b) {
   return "上传失败";
  }
//  更新数据库
  MediaFiles mediaFiles = addMediaFilesToDb(userId, fileMd5, uploadFileParamsDto, bucket_mediafiles, objectName);
  if (mediaFiles == null) {
   return "上传失败";
  }
//  返回
  return mediaFiles.getUrl();
 }

 /**
  * 将文件上传到minio
  *
  * @param localFilePath 文件本地路径
  * @param mimeType      媒体类型
  * @param bucket        桶
  * @param objectName    对象名
  * @return
  */
 @Override
 @Transactional
 public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
  try {
   UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
           .bucket(bucket)//桶
           .filename(localFilePath) //指定本地文件路径
           .object(objectName)//对象名 放在子目录下
           .contentType(mimeType)//设置媒体文件类型
           .build();
   System.out.println("222222222");
   System.out.println(bucket);

   //上传文件
   minioClient.uploadObject(uploadObjectArgs);
   System.out.println("333333333");
   log.debug("上传文件到minio成功,bucket:{},objectName:{},错误信息:{}", bucket, objectName);
   return true;
  } catch (Exception e) {
   e.printStackTrace();
   log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
  }
  System.out.println("444444444444444");
  return false;
 }
 /**
  * @param fileMd5             文件md5值
  * @param uploadFileParamsDto 上传文件的信息
  * @param bucket              桶
  * @param objectName          对象名称
  * @return com.xuecheng.media.model.po.MediaFiles
  * @description 将文件信息添加到文件表
  */
 @Transactional
 public MediaFiles addMediaFilesToDb(Long userId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
  //将文件信息保存到数据库
  MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
  if (mediaFiles == null) {
   mediaFiles = new MediaFiles();
   BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
   //文件id
   mediaFiles.setId(fileMd5);
   //机构id
   mediaFiles.setUserId(userId);
   //桶
   mediaFiles.setBucket(bucket);
   //file_path
   mediaFiles.setFilePath(objectName);
   //file_id
   mediaFiles.setFileId(fileMd5);
   //url
   mediaFiles.setUrl("/" + bucket + "/" + objectName);
   //上传时间
   mediaFiles.setCreateDate(LocalDateTime.now());
   //插入数据库
   int insert = mediaFilesMapper.insert(mediaFiles);
   if (insert <= 0) {
    log.debug("向数据库保存文件失败,bucket:{},objectName:{}", bucket, objectName);
    return null;
   }
   System.out.println("555555555555555");
   return mediaFiles;
  }
  return mediaFiles;
 }
 //获取文件默认存储目录路径 年/月/日
 private String getDefaultFolderPath() {
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  String folder = sdf.format(new Date()).replace("-", "/") + "/";
  return folder;
 }

 //获取文件的md5
 private String getFileMd5(File file) {
  try (FileInputStream fileInputStream = new FileInputStream(file)) {
   String fileMd5 = DigestUtils.md5Hex(fileInputStream);
   return fileMd5;
  } catch (Exception e) {
   e.printStackTrace();
   return null;
  }
 }
 //根据扩展名获取mimeType
 private String getMimeType(String extension) {
  if (extension == null) {
   extension = "";
  }
  //根据扩展名取出mimeType
  ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
  String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
  if (extensionMatch != null) {
   mimeType = extensionMatch.getMimeType();
  }
  return mimeType;
 }
}

