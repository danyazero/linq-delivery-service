package org.zero.npservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zero.npservice.model.Delivery;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.service.DeliveryService;
import org.zero.npservice.service.NovaPostService;
import org.zero.npservice.utils.Base64Decoder;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DeliveryService deliveryService;

//    @DeleteMapping
//    void removeDeliveryDocument(@RequestBody TestController.Data data) {
//        novaPostService.removeDeliveryDocument(data.data);
//    }

    @GetMapping
    double calculateDeliveryPrice(
            @RequestParam String sender,
            @RequestParam String recipient,
            @RequestParam Double price,
            @RequestParam Integer parcelType
    ) {
        return deliveryService.calculatePrice(new Delivery(
                Base64Decoder.apply(sender),
                Base64Decoder.apply(recipient),
                price, parcelType));
    }
}
