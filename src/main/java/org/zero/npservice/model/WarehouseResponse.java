package org.zero.npservice.model;

public record WarehouseResponse (
        Integer id,
        String shortAddress,
        Integer warehouseNumber
) {
}
