package com.yyb.study.demo01;

import com.rabbitmq.client.Channel;
import com.yyb.study.utils.ConnectUtils;

public class Producer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String mess = "hello world";
        channel.basicPublish("", QUEUE_NAME, null, mess.getBytes());

        System.out.println("消息发送完毕");

    }
}
