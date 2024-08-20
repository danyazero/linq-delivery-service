package org.zero.npservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zero.npservice.exception.RequestException;
import org.zero.npservice.model.WarehouseCouple;
import org.zero.npservice.repository.NpWarehouseRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WarehouseCoupleHandler {
    private final NpWarehouseRepository warehouseRepository;

    public WarehouseCouple get(Integer senderWarehouseId, Integer recipientWarehouseId) {
        var result = warehouseRepository.getWarehouseCouple(senderWarehouseId, recipientWarehouseId);
        var senderWarehouse = result.stream().filter(element -> !Objects.equals(element.getId(), senderWarehouseId)).findFirst();
        var recipientWarehouse = result.stream().filter(element -> !Objects.equals(element.getId(), recipientWarehouseId)).findFirst();
        if (senderWarehouse.isEmpty() || recipientWarehouse.isEmpty()) throw new RequestException("Warehouse not founded");

        return new WarehouseCouple(senderWarehouse.get(), recipientWarehouse.get());
    }
}
