package com.yyb.springboot.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable("message") String message) {
        System.out.println("当前时间：" + new Date().toString() + "，发送的消息是：" + message);
        rabbitTemplate.convertAndSend("xExchange", "XA", "消息来自ttl为10秒的队列：" + message);
        rabbitTemplate.convertAndSend("xExchange", "XB", "消息来自ttl为40秒的队列：" + message);

    }

    @GetMapping("/sendMsg/{message}/{ttl}")
    public void sendMsg(@PathVariable("message") String message, @PathVariable("ttl") String ttl) {
        System.out.println("当前时间：" + new Date().toString() + "，发送的消息是：" + message);
        rabbitTemplate.convertAndSend("xExchange", "XC", "消息来自队列C：" + message,
                msg -> {
                    msg.getMessageProperties().setExpiration(ttl);
                    return msg;
                });

    }

}
