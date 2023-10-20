package com.teenager.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teenager.content.mapper.QuestionRecordMapper;
import com.teenager.content.model.po.QuestionRecord;
import com.teenager.content.service.QuestionRecordService;
import org.springframework.stereotype.Service;

/**
 * @author Xue
 * @create 2023-05-07-11:53
 */
@Service
public class QuestionRecordServiceImpl extends ServiceImpl<QuestionRecordMapper, QuestionRecord> implements QuestionRecordService {
}
