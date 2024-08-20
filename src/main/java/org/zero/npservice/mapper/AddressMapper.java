package org.zero.npservice.mapper;

import org.zero.npservice.entity.Delivery;
import org.zero.npservice.model.delivery.novaPost.NPAddress;
import org.zero.npservice.model.delivery.novaPost.NPWarehouse;

public class AddressMapper {
  public static NPAddress mapAsSender(Delivery delivery) {
    return new NPAddress(
        delivery.getSenderWarehouseId(),
        delivery.getSenderCityId(),
        delivery.getSenderWarehouseNumber());
  }

  public static NPAddress map(NPWarehouse warehouse) {
    return new NPAddress(
        warehouse.getAddressRef(), warehouse.getCityRef(), warehouse.getWarehouseNumber());
  }

  public static NPAddress mapAsRecipient(Delivery delivery) {
    return new NPAddress(
        delivery.getRecipientWarehouseId(),
        delivery.getSenderCityId(),
        delivery.getSenderWarehouseNumber());
  }
}
