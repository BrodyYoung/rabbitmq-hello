package com.yyb.study.demo01;

import com.rabbitmq.client.*;
import com.yyb.study.utils.ConnectUtils;

public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();

        DeliverCallback d = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };

        CancelCallback c = consumerTag -> {
            System.out.println("消息被中断");
        };

        channel.basicConsume(QUEUE_NAME, true, d, c);
    }
}
