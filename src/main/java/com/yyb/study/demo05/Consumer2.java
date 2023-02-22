package com.yyb.study.demo05;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yyb.study.utils.ConnectUtils;

public class Consumer2 {
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        System.out.println("Consumer2等待接收消息的时间较短");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("C2接收到的消息：" + message);
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "key001");
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
