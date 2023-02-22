package com.yyb.study.demo03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yyb.study.utils.ConnectUtils;

import java.util.Scanner;

public class Producer {

    private final static String ACK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        channel.queueDeclare(ACK_QUEUE_NAME, true, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            System.out.println("生产者发送消息：" + message);
            channel.basicPublish("", ACK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        }

    }
}
