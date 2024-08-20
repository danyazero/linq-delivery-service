package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NPWarehouseRequest {
    @JsonProperty("TypeOfWarehouseRef")
    private String typeOfWarehouseRef;
}
