package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPWarehouse implements INPWarehouse {
    @JsonProperty("ShortAddress")
    private String shortAddress;
    @JsonProperty("CityRef")
    private String cityRef;
    @JsonProperty("CityDescription")
    private String cityDescription;
    @JsonProperty("SettlementAreaDescription")
    private String settlementAreaDescription;
    @JsonProperty("Number")
    private Integer warehouseNumber;
    @JsonProperty("Ref")
    private String addressRef;
    @JsonProperty("Longitude")
    private Double longitude;
    @JsonProperty("Latitude")
    private Double latitude;
    @JsonProperty("WarehouseIndex")
    private String warehouseIndex;
    @JsonProperty("SiteKey")
    private Integer siteKey;
}