package com.teenager.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teenager.content.mapper.ArticleMapper;
import com.teenager.content.model.po.Article;
import com.teenager.content.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * @author Xue
 * @create 2023-04-25-10:30
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
