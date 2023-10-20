package com.teenager.content.api;

import com.teenager.content.common.R;
import com.teenager.content.model.po.Article;
import com.teenager.content.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xue
 * @create 2023-04-25-10:54
 */
@Slf4j
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 获取最新热文
     * @return
     */
    @GetMapping("/newArticle")
    public R<List<Article>> newArticle(){
        List<Article> articles = articleService.list();
        return R.success(articles);
    }
}
