package org.zero.npservice.model.delivery.novaPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum NPDeliveryStatus {
    SELF_CREATED(1, "Автоматично створено накладну."),
    DELETED(2, "Видалено"),
    NOT_FOUNDED(3, "Номер не знайдено"),
    IN_CITY(4, "На шляху"),
    IN_CITY2(41, "На шляху"),
    GOING_CITY(5, "На шляху"),
    IN_CITY_WAIT(6, "Відправлення у місті."),
    ARRIVED(7, "Прибув на відділення"),
    ARRIVED_BOX(8, "Прибув на відділення"),
    RECEIVED(9, "Відправлення отримано"),
    PREPARES(12, "Нова Пошта комплектує ваше відправлення"),
    ON_WAY(101, "На шляху до одержувача"),
    REJECTION(102, "Відмова від отримання"),
    RECEIVER_REJECTION(103, "Відмова одержувача"),
    DESTINATION_CHANGED(104, "Змінено адресу"),
    STORING_STOPPED(105, "Припинено зберігання");

    private final int id;
    private final String title;

    NPDeliveryStatus(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static List<String> getFinishedStatuses() {
        List<String> finishedStatuses = new ArrayList<>();
        finishedStatuses.add(NPDeliveryStatus.DELETED.name());
        finishedStatuses.add(NPDeliveryStatus.RECEIVED.name());
        finishedStatuses.add(NPDeliveryStatus.REJECTION.name());
        finishedStatuses.add(NPDeliveryStatus.RECEIVER_REJECTION.name());

        return finishedStatuses;
    }

    public static NPDeliveryStatus findEnumById(int id) {
        return Arrays.stream(NPDeliveryStatus.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null); // Or throw an exception if not found
    }
}
