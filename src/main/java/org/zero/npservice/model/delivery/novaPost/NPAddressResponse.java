package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NPAddressResponse extends NPResponse {
    private List<NPResultObject> data;

    @Getter
    @Setter
    public static class NPResultObject {
        @JsonProperty("Ref")
        private String addressRef;
        @JsonProperty("Description")
        private String description;
    }
}
