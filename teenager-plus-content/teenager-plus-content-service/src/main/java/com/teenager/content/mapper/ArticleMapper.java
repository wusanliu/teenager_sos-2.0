package com.teenager.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teenager.content.model.po.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Xue
 * @create 2023-04-25-10:29
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
