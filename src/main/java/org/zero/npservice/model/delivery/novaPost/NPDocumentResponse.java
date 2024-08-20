package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPDocumentResponse extends NPResponse {
    List<NPDocument> data;

    @Setter
    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NPDocument {
        @JsonProperty("Ref")
        private String ref;
        @JsonProperty("CostOnSite")
        private Double costOnSite;
        @JsonProperty("EstimatedDeliveryDate")
        private String estimatedDeliveryDate;
        @JsonProperty("IntDocNumber")
        private String intDocNumber;
    }
}
