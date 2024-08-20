package org.zero.npservice.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.zero.npservice.model.kafka.DeliveryEvent;
import org.zero.npservice.model.kafka.data.Delivery;

@Component
@RequiredArgsConstructor
public class DeliveryKafkaProducer {
    private final KafkaTemplate<String, DeliveryEvent> kafkaTemplate;

    public void send(Delivery data) {
        var event = new DeliveryEvent("deliveryUpdate", 1, data);
        this.kafkaTemplate.send("delivery", event);
    }
}
