package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NPCalculate {
    @JsonProperty("CitySender")
    private String citySenderRef;
    @JsonProperty("CityRecipient")
    private String cityRecipientRef;
    @JsonProperty("Weight")
    private String weight;
    @JsonProperty("ServiceType")
    private final NPServiceType serviceType = NPServiceType.WarehouseWarehouse;
    @JsonProperty("CargoType")
    private final NPCargoType cargoType = NPCargoType.Parcel;
    @JsonProperty("SeatsAmount")
    private final String seatsAmount = "1";
    @JsonProperty("Cost")
    private String cost;
}
