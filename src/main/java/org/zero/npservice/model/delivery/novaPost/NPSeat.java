package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class NPSeat {

    @JsonProperty("volumetricVolume")
    private final String volumetricVolume = "1";
    @JsonProperty("volumetricWidth")
    private String width;

    @JsonProperty("volumetricLength")
    private String length;

    @JsonProperty("volumetricHeight")
    private String height;

    @JsonProperty("weight")
    private String weight;
}
