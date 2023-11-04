package com.teenager.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teenager.content.mapper.TempMessageMapper;
import com.teenager.content.model.po.TempMessage;
import com.teenager.content.service.TempMessageService;
import org.springframework.stereotype.Service;

/**
 * @author Xue
 * @create 2023-11-04-16:01
 */
@Service
public class TempMessageServiceImpl extends ServiceImpl<TempMessageMapper, TempMessage> implements TempMessageService {
}
