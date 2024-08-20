package org.zero.npservice.model.delivery;

import java.util.List;

public interface IDelivery<T> {
    double getCalculatedDeliveryPrice(String senderAddressRef, String recipientAddressRef, Parcel parcelDetails);
    String createContactPerson(ContactPerson contactPerson);
    T createAddress(String addressRef);
    Document createDeliveryDocument(ContactPerson contactSender, T addressSender, ContactPerson contactRecipient, T addressRecipient, Parcel parcelDetails);
    void removeDeliveryDocument(String orderId);
    Object getCurrentDeliveryStatus(List<String> deliveryDocumentNumber);
    boolean isSuitable(DeliveryProvider deliveryProvider);
}