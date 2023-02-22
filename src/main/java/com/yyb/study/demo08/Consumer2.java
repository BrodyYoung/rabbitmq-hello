package com.yyb.study.demo08;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yyb.study.utils.ConnectUtils;

public class Consumer2 {

    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("C2接收到的消息：" + message);
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, cancelCallback);
    }
}
