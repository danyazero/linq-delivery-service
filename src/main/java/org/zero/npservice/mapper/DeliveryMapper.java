package org.zero.npservice.mapper;

import org.zero.npservice.entity.Delivery;
import org.zero.npservice.entity.Parcel;
import org.zero.npservice.model.delivery.novaPost.NPAddress;
import org.zero.npservice.model.delivery.novaPost.NPCalculate;
import org.zero.npservice.model.kafka.data.Order;

public class DeliveryMapper {

  public static org.zero.npservice.model.kafka.data.Delivery map(Delivery delivery) {
    return null;
  }

  public static NPCalculate map(
      org.zero.npservice.model.delivery.Parcel parcelDetails,
      String senderCityRef,
      String recipientCityRef) {
    return new NPCalculate(
        senderCityRef,
        recipientCityRef,
        String.valueOf(parcelDetails.getWeight()),
        String.valueOf(parcelDetails.getCost()));
  }

  public static Delivery map(
      Order order, Parcel parcel, NPAddress senderAddress, NPAddress recipientAddress) {
    var delivery = new org.zero.npservice.entity.Delivery();
    delivery.setOrderId(order.getOrderId());
    delivery.setSenderUserId(order.getSellerUserId());
    delivery.setRecipientUserId(order.getRecipientUserId());

    delivery.setSenderWarehouseNumber(senderAddress.warehouseNumber());
    delivery.setSenderWarehouseId(senderAddress.addressRef());
    delivery.setSenderCityId(senderAddress.cityRef());

    delivery.setRecipientWarehouseNumber(recipientAddress.warehouseNumber());
    delivery.setRecipientWarehouseId(recipientAddress.addressRef());
    delivery.setRecipientCityId(recipientAddress.cityRef());

    delivery.setParcelTypeId(parcel);
    delivery.setDescription(order.getDeliveryDescription());
    delivery.setCartPrice(order.getCartPrice());

    delivery.setStatus("DEFINED");

    return delivery;
  }
}
