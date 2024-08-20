package org.zero.npservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zero.npservice.entity.NpWarehouse;
import org.zero.npservice.exception.RequestException;
import org.zero.npservice.model.Object;
import org.zero.npservice.model.ObjectDTO;
import org.zero.npservice.model.WarehouseCouple;
import org.zero.npservice.model.WarehouseResponse;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.repository.NpWarehouseRepository;
import org.zero.npservice.service.NovaPostService;
import org.zero.npservice.utils.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/warehouse")
public class WarehouseController {
    private final ObjectMapper objectMapper;
    private final UUIDProvider uuidProvider;
    private final NovaPostService novaPostService;
    private final UUIDWarehouseProvider uuidWarehouseProvider;
    private final NpWarehouseRepository warehouseRepository;
    private final WarehouseCoupleHandler warehouseCoupleHandler;

    @GetMapping("/couple/{senderId}/{recipientId}")
    public WarehouseCouple getWarehouseCouple(@PathVariable Integer recipientId, @PathVariable Integer senderId) {
        return warehouseCoupleHandler.get(senderId, recipientId);
    }

    @GetMapping("/area")
    public List<Object> getCityList(@RequestParam String id) {
        var city = uuidWarehouseProvider.get(id);
        return warehouseRepository.findAllByCityDescriptionAndCountryArea(city.city(), city.area()).stream().map(element -> new Object(uuidProvider.generate(element.getId()), "Відділення №"+element.getWarehouseNumber(), element.getShortAddress())).toList();
    }

    @GetMapping("/search/{value}")
    public List<Object> search(@PathVariable String value) {
        return warehouseRepository.findCityByCityDescriptionLikeIgnoreCase(value).stream().map(objectMapper).toList();
    }

    @GetMapping("/search")
    public List<Object> search() {
        return warehouseRepository.getPopularCities().stream().map(objectMapper).toList();
    }

//    @GetMapping()
//    public Double getById(@RequestParam String senderId, @RequestParam String recipientId, @RequestParam Double price, @RequestParam Double weight) {
//        var senderWarehouseId = uuidProvider.get(senderId);
//        var warehouseId = uuidProvider.get(recipientId);
//        var warehouseCouple = warehouseCoupleHandler.get(senderWarehouseId, warehouseId);
//        var parcel = new Parcel("розрахунок вартості", weight, 15.0, price);
//        return novaPostService.getCalculatedDeliveryPrice(warehouseCouple.sender().getCityRef(), warehouseCouple.recipient().getCityRef(), parcel);
//    }

}
