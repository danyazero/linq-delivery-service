package org.zero.npservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zero.npservice.model.delivery.Address;
import org.zero.npservice.model.delivery.ContactPerson;
import org.zero.npservice.model.delivery.Document;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.model.delivery.novaPost.NPAddress;
import org.zero.npservice.model.delivery.novaPost.NPWarehouse;
import org.zero.npservice.service.NovaPostService;

import java.util.List;

@RestController
@RequestMapping("/document1")
@RequiredArgsConstructor
public class TestController {
    private final NovaPostService novaPostService;

    @DeleteMapping
    void removeDeliveryDocument(@RequestBody Data data) {
        novaPostService.removeDeliveryDocument(data.data);
    }

//    @GetMapping
//    double calculateDeliveryPrice(@RequestBody Delivery delivery) {
//        var parcel = new Parcel("test", delivery.weight(), delivery.length(), delivery.cost());
//        return novaPostService.getCalculatedDeliveryPrice(delivery.senderCity(), delivery.recipientCity(), parcel);
//    }

    @GetMapping("/status")
    String getDeliveryStatus(@RequestBody Data data) {
        return novaPostService.getCurrentDeliveryStatus(List.of(data.data)).getFirst().getStatusCode();
    }

    public record Data(String data){}
    public record Delivery(String senderCity, String recipientCity, Double cost, Double weight, Double length){}

//    @PostMapping
//    Document createDeliveryDocument() {
//        var senderContact = new ContactPerson("Данііл", "Мозжухін", "Андрійович", "380960639371", true);
//        var senderAddress = new Address(null, "Миколаївська область", "Миколаїв", "Героїв України", "13", "58/1");
//        var recipientContact = new ContactPerson("Данііл", "Мозжухін", "Андрійович", "380960639371", false);
//        var recipientAddress = new Address(null, "Львівська область", "Східниця", "Золота Баня", "23", "75134/1");
//        var parcel = new Parcel("Чохол для телефону", 0.1, 23.0, 50.0);
//        return novaPostService.createDeliveryDocument(senderContact, senderAddress, recipientContact, recipientAddress, parcel);
//    }

//    @PostMapping
//    NPAddress createAddress(@RequestBody Address address) {
//        return novaPostService.createAddress(address);
//    }

    @PatchMapping
    void updateWarehouse() {
        novaPostService.updateWarehouseList();
    }

//    @GetMapping("/{enumId}")
//    NPDeliveryStatus test(@PathVariable String enumId) {
//        return novaPostService.getCurrentDeliveryStatus(enumId);
//    }


}
