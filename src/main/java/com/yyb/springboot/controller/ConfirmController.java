package com.yyb.springboot.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/confirm")
public class ConfirmController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable("message") String message) {
        CorrelationData cor = new CorrelationData("10086");
        System.out.println("当前时间：" + new Date().toString() + "，发送的消息是：" + message);
        rabbitTemplate.convertAndSend("confirm_exchange123", "key1", "消息：" + message, cor);

    }

}
