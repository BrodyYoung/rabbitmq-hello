package com.yyb.study.demo02;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yyb.study.utils.ConnectUtils;

public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        System.out.println("Consumer2等待接收消息");

        DeliverCallback d = (consumerTag, message) -> {
            System.out.println("C接收到的消息：" + new String(message.getBody()));
        };

        CancelCallback c = consumerTag -> {
            System.out.println("消息被中断");
        };

        channel.basicConsume(QUEUE_NAME, true, d, c);
    }
}
