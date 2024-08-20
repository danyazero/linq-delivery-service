package org.zero.npservice.model.delivery;

public record Document(
        Double cost,
        String deliveryDate,
        String documentNumber
) {
}
