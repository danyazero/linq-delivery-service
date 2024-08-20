package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NPAddressRequest {
    @JsonProperty("CounterpartyRef")
    private String counterpartyRef;
    @JsonProperty("StreetRef")
    private String streetRef;
    @JsonProperty("BuildingNumber")
    private String buildingNumber;
    @JsonProperty("Flat")
    private final String flat = "";
}
