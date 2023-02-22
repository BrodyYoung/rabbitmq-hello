package com.yyb.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitmqConfig {

    public static final String A_QUEUE = "aQueue";
    public static final String B_QUEUE = "bQueue";
    public static final String C_QUEUE = "cQueue";
    public static final String D_QUEUE = "dQueue";

    public static final String X_EXCHANGE = "xExchange";
    public static final String Y_EXCHANGE = "yExchange";

    @Bean("aQueue")
    public Queue aQueue() {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", Y_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 10000);
        return QueueBuilder.durable(A_QUEUE).withArguments(map).build();
    }

    @Bean("bQueue")
    public Queue bQueue() {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", Y_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 40000);
        return QueueBuilder.durable(B_QUEUE).withArguments(map).build();
    }

    @Bean("cQueue")
    public Queue cQueue() {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", Y_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(C_QUEUE).withArguments(map).build();
    }

    @Bean("dQueue")
    public Queue dQueue() {
        return QueueBuilder.durable(D_QUEUE).build();
    }

    @Bean("xExchange")
    public DirectExchange xExchange() {

        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange() {

        return new DirectExchange(Y_EXCHANGE);
    }

    @Bean
    public Binding aQueueBindX(@Qualifier("aQueue") Queue aQueue, @Qualifier("xExchange") DirectExchange xExchange) {

        return BindingBuilder.bind(aQueue).to(xExchange).with("XA");
    }

    @Bean
    public Binding bQueueBindX(@Qualifier("bQueue") Queue bQueue, @Qualifier("xExchange") DirectExchange xExchange) {

        return BindingBuilder.bind(bQueue).to(xExchange).with("XB");
    }

    @Bean
    public Binding cQueueBindX(@Qualifier("cQueue") Queue cQueue, @Qualifier("xExchange") DirectExchange xExchange) {

        return BindingBuilder.bind(cQueue).to(xExchange).with("XC");
    }

    @Bean
    public Binding dQueueBindY(@Qualifier("dQueue") Queue dQueue, @Qualifier("yExchange") DirectExchange yExchange) {

        return BindingBuilder.bind(dQueue).to(yExchange).with("YD");
    }
}

