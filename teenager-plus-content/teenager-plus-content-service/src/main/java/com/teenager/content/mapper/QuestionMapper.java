package com.teenager.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teenager.content.model.po.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Xue
 * @create 2023-05-07-10:13
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
