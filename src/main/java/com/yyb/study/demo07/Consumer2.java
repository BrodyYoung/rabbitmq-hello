package com.yyb.study.demo07;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yyb.study.utils.ConnectUtils;

public class Consumer2 {

    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = "Queue2";
        channel.queueDeclare(queueName, false, false, false, null);

        channel.queueBind(queueName, EXCHANGE_NAME, "#.end");
        channel.queueBind(queueName, EXCHANGE_NAME, "start.*.*");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("C2接收到的消息：" + message);
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
