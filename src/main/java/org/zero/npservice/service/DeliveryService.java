package org.zero.npservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zero.npservice.exception.RequestException;
import org.zero.npservice.mapper.AddressMapper;
import org.zero.npservice.mapper.ContactPersonMapper;
import org.zero.npservice.mapper.ParcelMapper;
import org.zero.npservice.model.Delivery;
import org.zero.npservice.model.UserData;
import org.zero.npservice.model.delivery.ContactPerson;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.model.kafka.data.Order;
import org.zero.npservice.repository.DeliveryRepository;
import org.zero.npservice.repository.ParcelRepository;
import org.zero.npservice.utils.UUIDProvider;
import org.zero.npservice.utils.UserProvider;
import org.zero.npservice.utils.WarehouseCoupleHandler;

@Service
@RequiredArgsConstructor
public class DeliveryService {
  private final UserProvider userProvider;
  private final UUIDProvider uuidProvider;
  private final NovaPostService novaPostService;
  private final ParcelRepository parcelRepository;
  private final DeliveryRepository deliveryRepository;
  private final WarehouseCoupleHandler warehouseCoupleHandler;

  public void defineDocument(Order order) {
    var delivery = new org.zero.npservice.entity.Delivery();
    delivery.setOrderId(order.getOrderId());
    delivery.setSenderUserId(order.getSellerUserId());
    delivery.setRecipientUserId(order.getRecipientUserId());

    this.setRecipientDeliveryData(order, delivery);
    this.setSenderDeliveryData(order, delivery);

    delivery.setParcelTypeId(parcelRepository.getReferenceById(order.getParcelType()));
    delivery.setDescription(order.getDeliveryDescription());
    delivery.setCartPrice(order.getCartPrice());

    delivery.setStatus("DEFINED");
    deliveryRepository.save(delivery);
  }

  public void createDocument(String orderId) {
    var delivery = deliveryRepository.findFirstByOrderId(orderId);
    if (delivery.isEmpty()) throw new RequestException("Delivery not found");

    UserData senderUserData = userProvider.getUserData(delivery.get().getSenderUserId());
    UserData recipientUserData = userProvider.getUserData(delivery.get().getRecipientUserId());

    ContactPerson senderContactPerson = ContactPersonMapper.map(senderUserData);
    ContactPerson recipientContactPerson = ContactPersonMapper.map(recipientUserData);

    var senderAddress = AddressMapper.mapAsSender(delivery.get());
    var recipientAddress = AddressMapper.mapAsRecipient(delivery.get());

    org.zero.npservice.entity.Parcel parcelDetails = delivery.get().getParcelTypeId();
    Parcel parcel =
        ParcelMapper.map(
            parcelDetails, delivery.get().getDescription(), delivery.get().getCartPrice());

    novaPostService.createDeliveryDocument(
        senderContactPerson, senderAddress, recipientContactPerson, recipientAddress, parcel);
  }

  public Double calculatePrice(Delivery delivery) {
    var parcelDetails = parcelRepository.findById(delivery.parcelType());
    if (parcelDetails.isEmpty()) throw new RequestException("Parcel not found");

    var parcel = ParcelMapper.map(parcelDetails.get(), "test", delivery.price());
    var senderWarehouseId = uuidProvider.get(delivery.sender());
    var recipientWarehouseId = uuidProvider.get(delivery.recipient());
    var warehouseCouple = warehouseCoupleHandler.get(senderWarehouseId, recipientWarehouseId);

    return novaPostService.getCalculatedDeliveryPrice(
        warehouseCouple.sender().getCityRef(), warehouseCouple.recipient().getCityRef(), parcel);
  }

  private void setRecipientDeliveryData(Order order, org.zero.npservice.entity.Delivery delivery) {
    var senderAddress = novaPostService.createAddress(order.getSellerWarehouseId());
    delivery.setSenderWarehouseNumber(senderAddress.warehouseNumber());
    delivery.setSenderWarehouseId(senderAddress.addressRef());
    delivery.setSenderCityId(senderAddress.cityRef());
  }

  private void setSenderDeliveryData(Order order, org.zero.npservice.entity.Delivery delivery) {
    var recipientAddress = novaPostService.createAddress(order.getRecipientWarehouseId());
    delivery.setRecipientWarehouseNumber(recipientAddress.warehouseNumber());
    delivery.setRecipientWarehouseId(recipientAddress.addressRef());
    delivery.setRecipientCityId(recipientAddress.cityRef());
  }
}
