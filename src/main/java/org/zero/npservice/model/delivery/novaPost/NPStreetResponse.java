package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NPStreetResponse extends NPResponse {
    private List<NPResultObject> data;

    @Getter
    @Setter
    public static class NPResultObject {
        @JsonProperty("Description")
        private String description;
        @JsonProperty("Ref")
        private String streetRef;
        @JsonProperty("StreetsTypeRef")
        private String streetsTypeRef;
        @JsonProperty("StreetsType")
        private String streetsType;
    }
}


//Description": "Українська",
//			"Ref": "ecb3bc0e-972f-11e5-a023-005056887b8d",
//			"StreetsTypeRef": "Street",
//			"StreetsType": "вул."
