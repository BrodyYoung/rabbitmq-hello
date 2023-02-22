package com.yyb.study.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectUtils {

    public static Channel connect() throws Exception {

        ConnectionFactory fact = new ConnectionFactory();
        fact.setHost("192.168.1.100");
        fact.setUsername("guest");
        fact.setPassword("guest");

        Connection connection = fact.newConnection();
        Channel channel = connection.createChannel();
        return channel;

    }
}
