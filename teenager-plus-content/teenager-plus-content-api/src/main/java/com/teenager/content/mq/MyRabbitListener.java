package com.teenager.content.mq;

import com.teenager.content.common.SmsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
//使用queuesToDeclare属性，如果不存在则会创建队列
//@RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.RABBITMQ_DEMO_TOPIC))
public class MyRabbitListener {
    @RabbitListener(queues = RabbitMQConfig.RABBITMQ_DEMO_TOPIC)
    public void listen1(Map<String,Object> map) throws InterruptedException{
        System.out.println("收到消息："+map.toString());
//        String message=(String) map.get("msg");
//        if (message.equals("发送短信")){
//            log.info("发送短信");
//            //        SmsSender.sendSms("111","111","111","111");
//        }
    }
}