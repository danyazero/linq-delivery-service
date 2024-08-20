package org.zero.npservice.model.kafka.data;

import lombok.Getter;
import lombok.Setter;
import org.zero.npservice.model.delivery.novaPost.NPDeliveryStatus;

@Getter
@Setter
public class Delivery {
    private String orderId;
    private NPDeliveryStatus deliveryStatus;
    private Double deliveryPrice;
}
