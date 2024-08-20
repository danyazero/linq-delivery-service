package org.zero.npservice.model;

public record WarehouseCouple(
        Warehouse sender,
        Warehouse recipient
) {
}
