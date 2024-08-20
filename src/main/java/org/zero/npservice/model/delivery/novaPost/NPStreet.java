package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NPStreet(
        @JsonProperty("FindByString")
        String streetName,
        @JsonProperty("CityRef")
        String cityRef
) {
}
