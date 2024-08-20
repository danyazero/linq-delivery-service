package org.zero.npservice.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import org.zero.npservice.model.delivery.ContactPerson;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.model.delivery.novaPost.NPAddress;
import org.zero.npservice.model.delivery.novaPost.NPDocumentRequest;
import org.zero.npservice.model.delivery.novaPost.NPSeat;

@Component
public class DocumentProvider {
  private NPDocumentRequest document;

  public DocumentProvider addRecipientAddress(NPAddress address) {
    this.document.setRecipientWarehouseIndex(String.valueOf(address.warehouseNumber()));
    this.document.setSenderAddressRef(address.addressRef());
    this.document.setCityRecipientRef(address.cityRef());
    return this;
  }

  public DocumentProvider addSenderAddress(NPAddress address) {
    this.document.setSenderWarehouseIndex(String.valueOf(address.warehouseNumber()));
    this.document.setSenderAddressRef(address.addressRef());
    this.document.setCitySenderRef(address.cityRef());
    return this;
  }

  public DocumentProvider addParcelDetails(Parcel parcelDetails) {
    this.document.setWeight(String.valueOf(parcelDetails.getWeight()));
    this.document.setDescription(parcelDetails.getDescription());
    this.document.setCost(String.valueOf(parcelDetails.getCost()));
    return this;
  }

  public DocumentProvider addCounterpartyRefs(
      String senderCounterpartyRef, String recipientCounterpartyRef) {
    this.document.setSenderRef(senderCounterpartyRef);
    this.document.setRecipientRef(recipientCounterpartyRef);

    return this;
  }

  public DocumentProvider addContactRefs(String sender, String recipient) {
    this.document.setContactSenderRef(sender);
    this.document.setContactSenderRef(recipient);

    return this;
  }

  public DocumentProvider addContactPersonPhones(ContactPerson sender, ContactPerson recipient) {
    this.document.setSendersPhone(sender.getPhone());
    this.document.setRecipientsPhone(recipient.getPhone());

    return this;
  }

  public DocumentProvider addSeats(List<NPSeat> seats) {
    this.document.setOptionsSeat(seats);

    return this;
  }

  public DocumentProvider addDays(Integer days) {
    var currentDate = Instant.now();
    var date = currentDate.plus(Duration.ofDays(days));
    var dateOfSend = Date.from(date);
    var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    this.document.setDateTime(dateFormatter.format(dateOfSend));

    return this;
  }

  public NPDocumentRequest build() {
    return this.document;
  }
}
