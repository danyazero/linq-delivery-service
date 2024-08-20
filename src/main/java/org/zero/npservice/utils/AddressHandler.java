package org.zero.npservice.utils;

import org.zero.npservice.exception.RequestException;
import org.zero.npservice.model.delivery.Address;

public class AddressHandler {
    public static Address decompose(String countryArea, String shortAddress, String warehouseNumber) {
        shortAddress = shortAddress.replaceAll("\\(.+\\)", "");
        String[] yard = shortAddress.split(",(\\s+)?");
        if (yard.length < 2) throw new RequestException("Incorrect street address");
        yard[2] = yard[2].trim().replaceAll("буд\\.?\\s*", "").trim();
        return new Address(null, countryArea, yard[0], yard[1], yard[2], warehouseNumber);
    }
}
