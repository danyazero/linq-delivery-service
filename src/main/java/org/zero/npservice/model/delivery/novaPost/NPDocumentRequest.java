package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class NPDocumentRequest {
  @JsonProperty("SenderWarehouseIndex")
  private String senderWarehouseIndex;

  @JsonProperty("RecipientWarehouseIndex")
  private String recipientWarehouseIndex;

  @JsonProperty("PayerType")
  private final NPPayerType payerType = NPPayerType.Sender;

  @JsonProperty("PaymentMethod")
  private final NPPaymentMethod paymentMethod = NPPaymentMethod.Cash;

  @JsonProperty("DateTime")
  private String dateTime;

  @JsonProperty("CargoType")
  private final NPCargoType cargoType = NPCargoType.Parcel;

  @JsonProperty("Weight")
  private String weight;

  @JsonProperty("ServiceType")
  private final NPServiceType serviceType = NPServiceType.WarehouseWarehouse;

  @JsonProperty("SeatsAmount")
  public final String seatsAmount = "1";

  @JsonProperty("Description")
  private String description;

  @JsonProperty("Cost")
  private String cost;

  @JsonProperty("CitySender")
  private String citySenderRef;

  @JsonProperty("Sender")
  private String senderRef;

  @JsonProperty("SenderAddress")
  private String senderAddressRef;

  @JsonProperty("ContactSender")
  private String contactSenderRef;

  @JsonProperty("SendersPhone")
  private String sendersPhone;

  @JsonProperty("CityRecipient")
  private String cityRecipientRef;

  @JsonProperty("Recipient")
  private String recipientRef;

  @JsonProperty("RecipientAddress")
  private String recipientAddressRef;

  @JsonProperty("ContactRecipient")
  private String contactRecipientRef;

  @JsonProperty("RecipientsPhone")
  private String recipientsPhone;

  @JsonProperty("OptionsSeat")
  private List<NPSeat> optionsSeat;

  public NPDocumentRequest(
      String senderWarehouseIndex,
      String recipientWarehouseIndex,
      Integer daysToSend,
      String weight,
      String description,
      String cost,
      String citySenderRef,
      String senderRef,
      String senderAddressRef,
      String contactSenderRef,
      String sendersPhone,
      String cityRecipientRef,
      String recipientRef,
      String recipientAddressRef,
      String contactRecipientRef,
      String recipientsPhone,
      List<NPSeat> optionsSeat) {
    this.senderWarehouseIndex = senderWarehouseIndex;
    this.recipientWarehouseIndex = recipientWarehouseIndex;
    var currentDate = Instant.now();
    var date = currentDate.plus(Duration.ofDays(daysToSend));
    var dateOfSend = Date.from(date);
    var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    this.dateTime = dateFormatter.format(dateOfSend);
    this.weight = weight;
    this.description = description;
    this.cost = cost;
    this.citySenderRef = citySenderRef;
    this.senderRef = senderRef;
    this.senderAddressRef = senderAddressRef;
    this.contactSenderRef = contactSenderRef;
    this.sendersPhone = sendersPhone;
    this.cityRecipientRef = cityRecipientRef;
    this.recipientRef = recipientRef;
    this.recipientAddressRef = recipientAddressRef;
    this.contactRecipientRef = contactRecipientRef;
    this.recipientsPhone = recipientsPhone;
    this.optionsSeat = optionsSeat;
  }
}
