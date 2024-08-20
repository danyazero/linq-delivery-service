package org.zero.npservice.model.kafka;

import lombok.Getter;
import lombok.Setter;
import org.zero.npservice.model.kafka.data.Delivery;

@Getter
@Setter
public class DeliveryEvent extends Event {
    private Delivery data;

    public DeliveryEvent(String eventType, Integer eventVersion, Delivery data) {
        super(eventType, eventVersion);
        this.data = data;
    }
}
