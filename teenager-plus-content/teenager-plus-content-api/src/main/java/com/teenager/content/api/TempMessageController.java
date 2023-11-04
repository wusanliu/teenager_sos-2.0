package com.teenager.content.api;

import com.teenager.content.common.R;
import com.teenager.content.model.po.TempMessage;
import com.teenager.content.service.TempMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xue
 * @create 2023-11-04-16:02
 */
@Controller
@RequestMapping("/message")
@RestController
@Slf4j
public class TempMessageController {
    @Autowired
    private TempMessageService tempMessageService;

    @GetMapping("/")
    public R<List<TempMessage>> get(){
        List<TempMessage> list = tempMessageService.list();
//        log.info(list.toString());
        return R.success(list);
    }
}
