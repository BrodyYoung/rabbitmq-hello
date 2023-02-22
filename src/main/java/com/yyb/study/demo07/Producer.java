package com.yyb.study.demo07;

import com.rabbitmq.client.Channel;
import com.yyb.study.utils.ConnectUtils;

import java.util.Scanner;

public class Producer {

    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            System.out.println("生产者发送消息：" + message);
            channel.basicPublish(EXCHANGE_NAME, "start.sss.so", null, message.getBytes("UTF-8"));
        }

    }
}
