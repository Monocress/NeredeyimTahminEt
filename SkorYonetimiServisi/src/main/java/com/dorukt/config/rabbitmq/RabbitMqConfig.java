package com.dorukt.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private String tahminQueue = "tahmin-queue";
    private String exchange = "direct-exchange";
    private String tahminBindingKey= "tahmin-key";

    @Bean
    Queue tahminQueue(){
        return new Queue(tahminQueue);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    Binding tahminBinding(final DirectExchange exchange,final Queue tahminQueue){
        return BindingBuilder.bind(tahminQueue).to(exchange).with(tahminBindingKey);
    }
}
