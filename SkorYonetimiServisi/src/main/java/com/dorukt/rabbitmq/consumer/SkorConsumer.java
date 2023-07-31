package com.dorukt.rabbitmq.consumer;

import com.dorukt.rabbitmq.model.SkorModel;
import com.dorukt.service.SkorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkorConsumer {


    private final SkorService skorService;

    @RabbitListener(queues = "tahmin-queue")
    public void consumeSkor(SkorModel model){
        skorService.guncelle(model);
    }
}
