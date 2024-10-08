package org.zero.npservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.zero.npservice.entity.Delivery;
import org.zero.npservice.exception.RequestException;
import org.zero.npservice.mapper.AddressMapper;
import org.zero.npservice.mapper.DeliveryMapper;
import org.zero.npservice.mapper.ParcelMapper;
import org.zero.npservice.model.delivery.ContactPerson;
import org.zero.npservice.model.delivery.DeliveryProvider;
import org.zero.npservice.model.delivery.Document;
import org.zero.npservice.model.delivery.IDelivery;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.model.delivery.novaPost.NPAddress;
import org.zero.npservice.model.delivery.novaPost.NPCalculate;
import org.zero.npservice.model.delivery.novaPost.NPCalculateResponse;
import org.zero.npservice.model.delivery.novaPost.NPContactResponse;
import org.zero.npservice.model.delivery.novaPost.NPDeliveryStatus;
import org.zero.npservice.model.delivery.novaPost.NPDocumentRemoveRequest;
import org.zero.npservice.model.delivery.novaPost.NPDocumentRequest;
import org.zero.npservice.model.delivery.novaPost.NPMethod;
import org.zero.npservice.model.delivery.novaPost.NPModel;
import org.zero.npservice.model.delivery.novaPost.NPObject;
import org.zero.npservice.model.delivery.novaPost.NPResponse;
import org.zero.npservice.model.delivery.novaPost.NPSeat;
import org.zero.npservice.model.delivery.novaPost.NPStatusRequest;
import org.zero.npservice.model.delivery.novaPost.NPStatusResponse;
import org.zero.npservice.model.delivery.novaPost.NPStatusResponse.NPStatusResult;
import org.zero.npservice.model.delivery.novaPost.NPWarehouseRequest;
import org.zero.npservice.model.delivery.novaPost.NPWarehouseResponse;
import org.zero.npservice.model.delivery.novaPost.NPWarehouseType;
import org.zero.npservice.repository.DeliveryRepository;
import org.zero.npservice.repository.NpWarehouseRepository;
import org.zero.npservice.utils.DocumentProvider;
import org.zero.npservice.utils.UUIDProvider;
import org.zero.npservice.utils.UpdateWarehouse;

@Component
@RequiredArgsConstructor
public class NovaPostService implements IDelivery<NPAddress> {
  private final UUIDProvider uuidProvider;
  private final UpdateWarehouse updateWarehouse;
  private final DeliveryRepository deliveryRepository;
  private final NpWarehouseRepository warehouseRepository;
  private final DocumentProvider documentProvider;

  @Value("${post.novapost.apiKey}")
  private String apiKey;

  @Value("${post.novapost.apiURI}")
  private String apiURI;

  @Value("${post.novapost.counterparty.recipient}")
  private String recipientCounterpartyRef;

  @Value("${post.novapost.deliveryDays}")
  private int deliveryDays;

  @Value("${post.novapost.counterparty.sender}")
  private String senderCounterpartyRef;

  @Override
  public double getCalculatedDeliveryPrice(
      String senderCityRef, String recipientCityRef, Parcel parcelDetails) {
    var requestBody = getPriceRequestBody(senderCityRef, recipientCityRef, parcelDetails);
    var response = sendPostRequest(requestBody, NPCalculateResponse.class);
    if (!response.getSuccess()) throw new RequestException("Exception price calculating.");
    return Double.parseDouble(response.getData().getFirst().getCost());
  }

  @Override
  @SneakyThrows
  public String createContactPerson(ContactPerson contactPerson) {
    setCounterparty(contactPerson);

    var requestBody =
        new NPObject<>(apiKey, NPModel.ContactPersonGeneral, NPMethod.save, contactPerson);
    var response = sendPostRequest(requestBody, NPContactResponse.class);
    return response.getData().getLast().getRef();
  }

  @Override
  public NPAddress createAddress(String addressRef) {
    // var warehouseId = uuidProvider.get(addressRef);
    var warehouse = warehouseRepository.findFirstByAddressRef(addressRef);
    if (warehouse.isEmpty()) throw new RequestException("Warehouse not found.");

    return AddressMapper.map(warehouse.get());
  }

  @Override
  public Document createDeliveryDocument(
      ContactPerson contactSender,
      NPAddress addressSender,
      ContactPerson contactRecipient,
      NPAddress addressRecipient,
      Parcel parcelDetails) {

    var requestBody =
        getDocumentRequestBody(
            contactSender, addressSender, contactRecipient, addressRecipient, parcelDetails);
    // var response = sendPostRequest(requestBody, NPDocumentResponse.class);
    // if (!response.getSuccess())
    // throw new RequestException("Error creating delivery document");
    System.out.println(requestBody.toString());
    return new Document(95.0, "date", "documentNumber", "");

    // var cost = response.getData().getFirst().getCostOnSite();
    // var date = response.getData().getFirst().getEstimatedDeliveryDate();
    // var documentNumber = response.getData().getFirst().getIntDocNumber();
    //
    // return new Document(cost, date, documentNumber);
  }

  @Override
  public void removeDeliveryDocument(String orderId) {

    var requestBody = getRemoveDocumentRequestBody(orderId);
    var response = sendPostRequest(requestBody, NPResponse.class);
    if (!response.getSuccess()) throw new RequestException("Error removing delivery document");
  }

  @Override
  public List<NPStatusResponse.NPStatusResult> getCurrentDeliveryStatus(
      List<String> deliveryDocumentNumber) {
    var requestBody = getDeliveryStatusRequestBody(deliveryDocumentNumber);
    var response = sendPostRequest(requestBody, NPStatusResponse.class);
    return response.getData();
  }

  // @Scheduled(fixedDelay = 10000)
  private void updateDeliveryStatus() {
    System.out.println("Started delivery status update");
    var deliveryList =
        deliveryRepository.findAllByStatusIsNotIn(NPDeliveryStatus.getFinishedStatuses());
    if (deliveryList.isEmpty()) return;

    List<String> deliveryDocumentNumberList =
        deliveryList.stream().map(element -> element.getDocument().getDocumentNumber()).toList();
    List<NPStatusResult> data = getCurrentDeliveryStatus(deliveryDocumentNumberList);

    List<Delivery> updated = new ArrayList<>();
    for (var resultStatus : data) {
      String currentDelivery = resultStatus.getDeliveryDocument();
      Optional<Delivery> previousDeliveryState =
          getPreviousDeliveryState(deliveryList, currentDelivery);

      if (isStatusChange(resultStatus, previousDeliveryState)) {
        String currentDeliveryStatus =
            NPDeliveryStatus.findEnumById(Integer.parseInt(resultStatus.getStatusCode())).name();
        System.out.println(currentDeliveryStatus);
        // TODO: Send message to kafka topic
        previousDeliveryState.get().setStatus(currentDeliveryStatus);
        updated.add(previousDeliveryState.get());
      }
    }

    deliveryRepository.saveAll(updated);
  }

  @Override
  public boolean isSuitable(DeliveryProvider deliveryProvider) {
    return false;
  }

  // @Scheduled(fixedRate = 5000)
  @Transactional
  public void updateWarehouseList() {

    CompletableFuture.supplyAsync(
            () -> {
              var requestBodyWarehouse =
                  new NPObject<Object>(
                      apiKey,
                      NPModel.AddressGeneral,
                      NPMethod.getWarehouses,
                      new NPWarehouseRequest(NPWarehouseType.WAREHOUSE.getRef()));
              return sendPostRequest(requestBodyWarehouse, NPWarehouseResponse.class).getData();
            })
        .thenAcceptAsync(updateWarehouse);

    CompletableFuture.supplyAsync(
            () -> {
              var requestBodyCargo =
                  new NPObject<Object>(
                      apiKey,
                      NPModel.AddressGeneral,
                      NPMethod.getWarehouses,
                      new NPWarehouseRequest(NPWarehouseType.CARGO.getRef()));
              return sendPostRequest(requestBodyCargo, NPWarehouseResponse.class).getData();
            })
        .thenAcceptAsync(updateWarehouse);

    System.out.println("Warehouse updated");
  }

  @SneakyThrows
  private <T, U> U sendPostRequest(NPObject<T> requestBody, Class<U> clazz) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<NPObject<T>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<U> response =
        new RestTemplate().exchange(apiURI, HttpMethod.POST, entity, clazz);

    if (response.getStatusCode() != HttpStatusCode.valueOf(200))
      throw new RequestException("Error delivery request.");
    return response.getBody();
  }

  private NPObject<NPCalculate> getPriceRequestBody(
      String senderCityRef, String recipientCityRef, Parcel parcelDetails) {
    var requestData = DeliveryMapper.map(parcelDetails, senderCityRef, recipientCityRef);
    return new NPObject<>(
        apiKey, NPModel.InternetDocumentGeneral, NPMethod.getDocumentPrice, requestData);
  }

  private void setCounterparty(ContactPerson contactPerson) {
    if (contactPerson.isSender()) contactPerson.setCounterpartyRef(senderCounterpartyRef);
    else contactPerson.setCounterpartyRef(recipientCounterpartyRef);
  }

  private NPObject<NPDocumentRequest> getDocumentRequestBody(
      ContactPerson contactSender,
      NPAddress addressSender,
      ContactPerson contactRecipient,
      NPAddress addressRecipient,
      Parcel parcelDetails) {

    String senderContactRef = this.createContactPerson(contactSender);
    String recipientContactRef = this.createContactPerson(contactRecipient);
    List<NPSeat> seats = new ArrayList<>(List.of(ParcelMapper.map(parcelDetails)));

    NPDocumentRequest document =
        documentProvider
            .addParcelDetails(parcelDetails)
            .addAddresses(addressSender, addressRecipient)
            .addCounterpartyRefs(senderCounterpartyRef, recipientCounterpartyRef)
            .addContactRefs(senderContactRef, recipientContactRef)
            .addContactPersonPhones(contactSender, contactRecipient)
            .addDays(deliveryDays)
            .addSeats(seats)
            .build();

    return new NPObject<>(apiKey, NPModel.InternetDocument, NPMethod.save, document);
  }

  private NPObject<NPDocumentRemoveRequest> getRemoveDocumentRequestBody(String orderId) {
    var requestData = new NPDocumentRemoveRequest(orderId);
    return new NPObject<>(apiKey, NPModel.InternetDocumentGeneral, NPMethod.delete, requestData);
  }

  private NPObject<NPStatusRequest> getDeliveryStatusRequestBody(
      List<String> deliveryDocumentNumber) {
    var statusRequestBody =
        new NPStatusRequest(
            deliveryDocumentNumber.stream().map(NPStatusRequest.NPStatus::new).toList());
    return new NPObject<>(
        apiKey, NPModel.TrackingDocumentGeneral, NPMethod.getStatusDocuments, statusRequestBody);
  }

  private static boolean isStatusChange(
      NPStatusResponse.NPStatusResult resultStatus, Optional<Delivery> previousDeliveryState) {
    return previousDeliveryState.isPresent()
        && !previousDeliveryState.get().getStatus().equals(resultStatus.getStatusCode());
  }

  private static Optional<Delivery> getPreviousDeliveryState(
      List<Delivery> deliveryList, String currentDelivery) {
    return deliveryList.stream()
        .filter(element -> !element.getDocument().getDocumentNumber().equals(currentDelivery))
        .findFirst();
  }
}
