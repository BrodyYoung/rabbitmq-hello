package com.yyb.study.demo08;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.yyb.study.utils.ConnectUtils;

public class Producer {

    private static final String EXCHANGE_NAME = "normal_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();

        AMQP.BasicProperties pro = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "消息" + i;

            System.out.println("生产者发送消息：" + message);
            channel.basicPublish(EXCHANGE_NAME, "zhangsan", pro, message.getBytes("UTF-8"));
        }

    }
}
