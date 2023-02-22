package com.yyb.springboot.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MsgConsumer {

    @RabbitListener(queues = "dQueue")
    public void deliverMsg(Message message, Channel channel) {
        String s = new String(message.getBody());
        System.out.println("当前时间：" + new Date().toString() + "，接收到的队列消息--->" + s);
    }

    @RabbitListener(queues = "confirm_queue")
    public void deliverMsg2(Message message, Channel channel) {
        String s = new String(message.getBody());
        System.out.println("当前时间：" + new Date().toString() + "，接收到的队列消息--->" + s);
    }
}
