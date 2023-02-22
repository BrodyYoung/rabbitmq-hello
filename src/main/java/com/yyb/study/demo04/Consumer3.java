package com.yyb.study.demo04;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yyb.study.utils.ConnectUtils;
import com.yyb.study.utils.SleepUtils;

public class Consumer3 {
    private static final String QUEUE_NAME = "052684ab-f137-4059-b768-f94d504a78cd";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        System.out.println("Consumer3等待接收消息的时间较短");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            SleepUtils.sleep(5);
            System.out.println("C3接收到的消息：" + message);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        channel.basicQos(1);
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
