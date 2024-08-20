package org.zero.npservice.model;

import org.springframework.web.bind.annotation.RequestParam;

public record DeliveryPrice(
        String sender,
        Double price,
        String recipient,
        Integer parcelType
) {
}
