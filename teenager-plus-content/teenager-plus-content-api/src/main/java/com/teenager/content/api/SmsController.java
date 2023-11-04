package com.teenager.content.api;

import com.teenager.content.common.R;
import com.teenager.content.common.SmsSender;
import com.teenager.content.config.TokenUtils;
import com.teenager.content.mq.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xue
 * @create 2023-11-03-20:59
 */
@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsController {

    @Autowired
    private RabbitMQService rabbitMQService;
    @PostMapping("/send")
    public R<String> SmsSend(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        TokenUtils.User user = TokenUtils.getUser(token);
        log.info(user.getUsername());
        rabbitMQService.sendMsg("发送短信");
        return R.success("发送成功");
    }
}
