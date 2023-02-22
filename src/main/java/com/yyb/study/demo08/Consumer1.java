package com.yyb.study.demo08;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yyb.study.utils.ConnectUtils;

import java.util.HashMap;
import java.util.Map;

public class Consumer1 {

    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String NORMAL_QUEUE = "normal_queue";

    private static final String DEAD_EXCHANGE = "dead_exchange";
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = ConnectUtils.connect();
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key", "lisi");
//        map.put("x-max-length", 6);

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, map);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");

        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String mes = new String(message.getBody(), "UTF-8");

            if (mes.equals("消息6")) {

                System.out.println("C1拒绝接收的消息：" + mes);
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {

                System.out.println("C1接收到的消息：" + mes);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

            }
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, cancelCallback);
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, cancelCallback);
    }
}
