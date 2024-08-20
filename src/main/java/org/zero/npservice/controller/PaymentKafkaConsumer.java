package org.zero.npservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.zero.npservice.model.kafka.PaymentEvent;
import org.zero.npservice.service.DeliveryService;

@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "payments", groupId = "delivery-service", containerFactory = "paymentListenerContainerFactory")
    public void consume(PaymentEvent paymentEvent) {
        deliveryService.createDocument(paymentEvent.getEventData().getOrderId());
    }
}
