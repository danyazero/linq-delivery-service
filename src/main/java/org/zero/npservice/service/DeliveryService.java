package org.zero.npservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zero.npservice.exception.RequestException;
import org.zero.npservice.mapper.AddressMapper;
import org.zero.npservice.mapper.ContactPersonMapper;
import org.zero.npservice.mapper.DeliveryMapper;
import org.zero.npservice.mapper.DocumentMapper;
import org.zero.npservice.mapper.ParcelMapper;
import org.zero.npservice.model.Delivery;
import org.zero.npservice.model.UserData;
import org.zero.npservice.model.delivery.ContactPerson;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.model.kafka.data.Order;
import org.zero.npservice.repository.DeliveryRepository;
import org.zero.npservice.repository.DocumentRepository;
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
  private final DocumentRepository documentRepository;

  public void defineDocument(Order order) {
    var senderAddress = novaPostService.createAddress(order.getSellerWarehouseId());
    var recipientAddress = novaPostService.createAddress(order.getRecipientWarehouseId());
    var parcel = parcelRepository.getReferenceById(order.getParcelType());
    var delivery = DeliveryMapper.map(order, parcel, senderAddress, recipientAddress);

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

    var parcelDetails = delivery.get().getParcelTypeId();
    Parcel parcel = ParcelMapper.map(parcelDetails, delivery.get());

    var document =
        novaPostService.createDeliveryDocument(
            senderContactPerson, senderAddress, recipientContactPerson, recipientAddress, parcel);
    var documentEntity = DocumentMapper.map(document);
    documentRepository.save(documentEntity);
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
}
