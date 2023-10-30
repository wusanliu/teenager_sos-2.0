package com.teenager.media.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Mr.M
 * @version 1.0
 * @description 文件信息
 * @date 2023/2/17 15:09
 */
@Data
@ToString
public class UploadFileParamsDto {


    private String filename;

    private String fileType;

    private Long fileSize;

    /**
     * 标签
     */
    private String tags;
    /**
     * 上传人
     */
    private String username;

}
