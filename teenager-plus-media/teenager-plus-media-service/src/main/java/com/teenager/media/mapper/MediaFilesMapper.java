package com.teenager.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teenager.media.model.po.MediaFiles;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 媒资信息 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface MediaFilesMapper extends BaseMapper<MediaFiles> {

}
