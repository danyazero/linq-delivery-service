package org.zero.npservice.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.zero.npservice.model.kafka.OrderEvent;
import org.zero.npservice.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderKafkaConsumer {
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "orders", groupId = "delivery-service", containerFactory = "orderListenerContainerFactory")
    public void listen(OrderEvent event) {
        var order = event.getEventData();
        System.out.println(order.toString());
        deliveryService.defineDocument(order);
    }
}
