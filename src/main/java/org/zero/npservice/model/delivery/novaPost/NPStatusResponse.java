package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPStatusResponse extends NPResponse {
    private List<NPStatusResult> data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NPStatusResult {
        @JsonProperty("StatusCode")
        private String statusCode;
        @JsonProperty("RecipientDateTime")
        private String recipientDateTime;
        @JsonProperty("TrackingUpdateDate")
        private String trackingUpdateDate;
        @JsonProperty("Number")
        private String deliveryDocument;
    }
}
