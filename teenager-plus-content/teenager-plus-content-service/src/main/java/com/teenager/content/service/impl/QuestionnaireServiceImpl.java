package com.teenager.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teenager.content.mapper.QuestionnaireMapper;
import com.teenager.content.model.po.Questionnaire;
import com.teenager.content.service.QuestionnaireService;
import org.springframework.stereotype.Service;

/**
 * @author Xue
 * @create 2023-05-07-10:17
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {
}
