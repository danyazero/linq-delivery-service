package org.zero.npservice.utils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UUIDWarehouseProvider {
    private final SyncEncryption syncEncryption;

    @SneakyThrows
    public String generate(String area, String city) {
        return syncEncryption.init().encrypt( area + "_" + city);
    }

    @SneakyThrows
    public City get(String uuid) {
        var city = syncEncryption.init().decrypt(uuid);
        var address = city.split("_");
        return new City(address[0], address[1]);
    }

    public record City(String area, String city){}
}
