package com.dorukt.rabbitmq.producer;

import com.dorukt.rabbitmq.model.SkorModel;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SkorProducer {
    private String exchange = "direct-exchange";
    private String tahminBindingKey= "tahmin-key";

    private final RabbitTemplate rabbitTemplate;

    public void skorUpdate(SkorModel skor){
        rabbitTemplate.convertAndSend(exchange,tahminBindingKey,skor);
    }

}
