package org.zero.npservice.utils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UUIDProvider {
    private final SyncEncryption syncEncryption;

    @SneakyThrows
    public String generate(Integer warehouseId) {
//        var time = new GregorianCalendar();
//        var salt = time.get(Calendar.DAY_OF_MONTH) + "" + time.get(Calendar.MONTH) + time.get(Calendar.HOUR) + time.get(Calendar.MINUTE);
        return syncEncryption.init().encrypt("sd_" + warehouseId);
    }

    @SneakyThrows
    public Integer get(String uuid) {
        var userId = syncEncryption.init().decrypt(uuid);
        System.out.println(userId);
        return Integer.parseInt(userId.split("_")[1]);
    }
}
