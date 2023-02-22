package com.yyb.study.demo02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yyb.study.utils.ConnectUtils;

import java.util.Scanner;

public class Producer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {

            String mess = scanner.next();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, mess.getBytes());
            System.out.println("消息发送完毕");

        }
    }
}
