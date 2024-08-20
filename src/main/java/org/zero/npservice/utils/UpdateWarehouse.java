package org.zero.npservice.utils;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.zero.npservice.entity.NpWarehouse;
import org.zero.npservice.model.delivery.novaPost.INPWarehouse;
import org.zero.npservice.model.delivery.novaPost.NPWarehouse;
import org.zero.npservice.repository.NpWarehouseRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class UpdateWarehouse implements Consumer<List<NPWarehouse>> {
    private final NpWarehouseRepository warehouseRepository;

    @Override
    public void accept(List<NPWarehouse> warehouses) {
        Timestamp start = new Timestamp(System.currentTimeMillis());
        List<NpWarehouse> data = warehouseRepository.findAll();
        warehouses.forEach(element -> element.setCityDescription(element.getCityDescription().replaceAll("\\(.+\\)", "").strip()));
        val citiesHashMap = getCityHashMapWithSiteKeyId(data);

        List<NpWarehouse> citiesToUpdate = new ArrayList<>();
        for (NPWarehouse warehouse : warehouses) {
            val currentCityForProcess = citiesHashMap.get(warehouse.getSiteKey());
            warehouse.setShortAddress(removeExcessWord(warehouse));
            var hash = getWarehouseHash(warehouse);

            if (isPresent(currentCityForProcess)) {
                currentCityForProcess.setShortAddress(removeExcessWord(warehouse));
                var warehouseHash = getWarehouseHash(currentCityForProcess);

                if (isEqualsHashCodes(warehouseHash, hash)) continue;

                citiesToUpdate.add(getCity(currentCityForProcess, hash));
            }
            else citiesToUpdate.add(getCity(warehouse, hash));
        }
        warehouseRepository.saveAll(citiesToUpdate);
        Timestamp end = new Timestamp(System.currentTimeMillis());
        System.out.println("Warehouse updated (" + warehouses.size() + ") - " + (end.getTime() - start.getTime()));
    }

    private String getWarehouseHash(INPWarehouse warehouse) {
        var str = warehouse.getWarehouseIndex() + warehouse.getWarehouseNumber() + warehouse.getShortAddress() + warehouse.getSettlementAreaDescription() + warehouse.getAddressRef();
        return SHAEncoder.apply(str, SHAEncoder.Encryption.SHA1);
    }

    private NpWarehouse getCity(INPWarehouse warehouse, String hash) {
        NpWarehouse newCity = new NpWarehouse();
        newCity.setShortAddress(warehouse.getShortAddress());
        newCity.setCityRef(warehouse.getCityRef());
        newCity.setWarehouseNumber(warehouse.getWarehouseNumber());
        newCity.setCityDescription(warehouse.getCityDescription());
        newCity.setSettlementAreaDescription(warehouse.getSettlementAreaDescription());
        newCity.setAddressRef(warehouse.getAddressRef());
        newCity.setWarehouseIndex(warehouse.getWarehouseIndex());
        newCity.setSiteKey(warehouse.getSiteKey());
        newCity.setHash(hash);
        newCity.setLatitude(warehouse.getLatitude());
        newCity.setLongitude(warehouse.getLongitude());
        return newCity;
    }

    private static boolean isEqualsHashCodes(String firstHash, String secondHash) {
        return firstHash.equals(secondHash);
    }

    private static boolean isPresent(Object result) {
        return result != null;
    }

    private static String removeExcessWord(NPWarehouse warehouse) {
        return warehouse.getShortAddress().replaceAll(",(\\s+)?Пункт(\\s+)?", "");
    }


    private static HashMap<Integer, NpWarehouse> getCityHashMapWithSiteKeyId(List<NpWarehouse> data) {
        HashMap<Integer, NpWarehouse> dataMap = new HashMap<>();
        for (NpWarehouse elem : data) {
            dataMap.put(elem.getSiteKey(), elem);
        }
        return dataMap;
    }
}
