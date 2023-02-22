package com.yyb.study.demo04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.yyb.study.utils.ConnectUtils;

import java.util.UUID;

public class Producer {

    public static void main(String[] args) throws Exception {

//        confirm01();  //单个发布确认耗时920ms
//        confirm02();    //批量发布确认耗时92ms
        confirm03();  // 批量发布确认耗时66ms
    }

    //单个发布确认
    public static void confirm01() throws Exception {
        Channel channel = ConnectUtils.connect();

        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = "消息" + i;

            System.out.println("生产者发送消息：" + message);
            channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));

            channel.waitForConfirms();
        }
        long end = System.currentTimeMillis();

        System.out.println("单个发布确认耗时" + (end - begin) + "ms");
    }

    //批量发布确认
    public static void confirm02() throws Exception {
        Channel channel = ConnectUtils.connect();

        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = "消息" + i;

            System.out.println("生产者发送消息：" + message);
            channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));
            if ((i + 1) % 100 == 0) {
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("批量发布确认耗时" + (end - begin) + "ms");
    }

    //异步发布确认
    public static void confirm03() throws Exception {
        Channel channel = ConnectUtils.connect();

        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        ConfirmCallback c1 = (deliveryTag, multiple) -> {
            System.out.println("确认的消息：" + deliveryTag);

        };

        ConfirmCallback c2 = (deliveryTag, multiple) -> {

            System.out.println("未确认的消息：" + deliveryTag);

        };
        channel.addConfirmListener(c1, c2);         // 异步通知
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = "消息" + i;

            System.out.println("生产者发送消息：" + message);
            channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));
        }
        long end = System.currentTimeMillis();

        System.out.println("批量发布确认耗时" + (end - begin) + "ms");
    }
}
