package com.teenager.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.content.common.R;
import com.teenager.content.model.po.Article;
import com.teenager.content.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getOne")
    public R<Article> getOne(Long id){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,id);
        Article article = articleService.getOne(queryWrapper);
        return R.success(article);
    }
    @PostMapping("/articles")
    public R<String> uploadArticle(@RequestBody Article article){
        articleService.save(article);
        return R.success("上传成功");
    }
}
