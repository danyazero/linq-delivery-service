package org.zero.npservice.model.delivery;

public record Address(
        String postCode,
        String countryArea,
        String city,
        String street,
        String houseNumber,
        String warehouseNumber
) {}
