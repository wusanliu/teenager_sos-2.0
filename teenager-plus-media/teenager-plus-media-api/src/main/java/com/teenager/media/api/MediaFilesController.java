package com.teenager.media.api;

import com.teenager.media.common.R;
import com.teenager.media.config.TokenUtils;
import com.teenager.media.model.dto.UploadFileParamsDto;
import com.teenager.media.model.dto.UploadFileResult;
import com.teenager.media.service.MediaFileService;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @description 媒资文件管理接口
 * @version 1.0
 */
 @RestController
public class MediaFilesController {


  @Autowired
  MediaFileService mediaFileService;


    @ApiOperation("上传图片")
    @RequestMapping(value = "/common/upload")
    public String uploadPic(@RequestPart("file")MultipartFile filedata,
                                      @RequestParam(value = "objectName",required=false) String objectName,
                                      HttpServletRequest request) throws IOException {
        System.out.println("111111111");
//     准备查询条件
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFileType("001001");//图片类型
        uploadFileParamsDto.setFileSize(filedata.getSize());
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());
//        接收到文件,创建临时文件
        File tempFile = File.createTempFile("minio", ".temp");
        filedata.transferTo(tempFile);
        System.out.println("aaaaaaaaaaaaaaa");
//     调用service上传图片
        String token = request.getHeader("token");
        TokenUtils.User user = TokenUtils.getUser(token);
        String absolutePath = tempFile.getAbsolutePath();
        System.out.println("bbbbbbbbbbbbbbbbbbbb");
        String url=mediaFileService.upload(user.getId(), uploadFileParamsDto, absolutePath,objectName);
        System.out.println("cccccccccccccccccc");
        System.out.println(url);
        if(url.equals("上传失败")){
            return "上传失败";
        }
        return url;
    }

}
