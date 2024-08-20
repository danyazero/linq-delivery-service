package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NPCalculateResponse extends NPResponse {
    private List<NPResultObject> data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NPResultObject {
        @JsonProperty("Cost")
        private String cost;
        @JsonProperty("CostRedelivery")
        private String costRedelivery;
        @JsonProperty("CostPack")
        private String costPack;
    }
}