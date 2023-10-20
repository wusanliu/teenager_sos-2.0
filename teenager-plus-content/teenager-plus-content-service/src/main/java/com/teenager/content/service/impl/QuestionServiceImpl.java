package com.teenager.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teenager.content.mapper.QuestionMapper;
import com.teenager.content.model.po.Question;
import com.teenager.content.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * @author Xue
 * @create 2023-05-07-10:16
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
}
